package info.igreque.keepmecontributingkt

class ContributionStatusChecker(private val onChanged: (CheckResult) -> Unit) {
    data class CheckResult(
        val contributor: CharSequence,
        val repositoryName: CharSequence,
        val contributionStatus: ContributionStatus
    )

    fun startPolling(contributor: CharSequence, repositoryName: CharSequence) {
        onChanged(CheckResult(contributor, repositoryName, ContributionStatus.UNKNOWN))
    }
}