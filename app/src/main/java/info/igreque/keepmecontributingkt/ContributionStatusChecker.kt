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
            onChanged(CheckResult(target, ContributionStatus.DONE))
            runBlocking {
                println(
                    gitHubClient.getLatestCommitDate(
                        target.repositoryName.toString(),
                        target.contributorName.toString()
                    ).toString()
                )
            }
        }
    }
}