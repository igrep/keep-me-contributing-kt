package info.igreque.keepmecontributingkt

import kotlinx.coroutines.runBlocking
import java.util.*

class ContributionStatusChecker(
    private val onChanged: (CheckResult) -> Unit,
    private val gitHubClient: GitHubClient,
    private val getCurrentTime: (Unit) -> Date
) {
    data class CheckResult(
        val target: CheckTarget,
        val contributionStatus: ContributionStatus
    )

    fun startPolling(target: CheckTarget) {
        if (target.isFormFilled()) {
            onChanged(CheckResult(target, ContributionStatus.Unknown))

            val beginningOfToday = Calendar.getInstance(Locale("ja", "JP", "JP")).run {
                time = getCurrentTime(Unit)
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
                time
            }
            runBlocking {
                val (contributionStatus, latestCommitDate) = try {
                    val fetchedDate = gitHubClient.getLatestCommitDate(
                        target.repositoryName.toString(),
                        target.contributorName.toString()
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
        }
    }
}