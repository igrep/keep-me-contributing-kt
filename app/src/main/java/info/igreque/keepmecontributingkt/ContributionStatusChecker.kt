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
            onChanged(CheckResult(target, ContributionStatus.UNKNOWN))

            val beginningOfToday = Calendar.getInstance(Locale("ja", "JP", "JP")).run {
                time = getCurrentTime(Unit)
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
                time
            }
            runBlocking {
                val latestCommitDate = gitHubClient.getLatestCommitDate(
                    target.repositoryName.toString(),
                    target.contributorName.toString()
                )

                val contributionStatus =
                    if (latestCommitDate > beginningOfToday) {
                        ContributionStatus.DONE
                    } else {
                        ContributionStatus.NOT_YET
                    }
                onChanged(CheckResult(target.updateLastCommitTime(latestCommitDate), contributionStatus))
            }
        }
    }
}