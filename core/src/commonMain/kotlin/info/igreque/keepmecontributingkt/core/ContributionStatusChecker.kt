package info.igreque.keepmecontributingkt.core

class ContributionStatusChecker(
    private val onChanged: (CheckResult) -> Unit,
    private val gitHubClient: GitHubClient,
    private val getBeginningOfToday: (Unit) -> Timestamp
) {
    data class CheckResult(
        val target: CheckTarget,
        val contributionStatus: ContributionStatus
    )

    suspend fun doCheck(target: CheckTarget) {
        if (!target.isFormFilled()) return

        val beginningOfToday = getBeginningOfToday(Unit)

        if (hasAlreadyCommittedAfter(target, beginningOfToday)) {
            onChanged(CheckResult(target, ContributionStatus.DoneNoCheck))
            return
        }

        onChanged(CheckResult(target, ContributionStatus.Unknown))

        val (contributionStatus, latestCommitDate) = try {
            val fetchedDate = gitHubClient.getLatestCommitDate(
                target.contributorName.toString(),
                target.repositoryName.toString()
            )

            if (fetchedDate > beginningOfToday) {
                Pair(ContributionStatus.Done, fetchedDate)
            } else {
                Pair(ContributionStatus.NotYet, fetchedDate)
            }
        } catch (e: Exception) {
            Pair(ContributionStatus.Error(e), target.lastCommitTime)
        }
        onChanged(CheckResult(target.updateLastCommitTime(latestCommitDate), contributionStatus))
    }

    private fun hasAlreadyCommittedAfter(target: CheckTarget, beginningOfToday: Timestamp) =
        target.lastCommitTime != null && target.lastCommitTime > beginningOfToday
}