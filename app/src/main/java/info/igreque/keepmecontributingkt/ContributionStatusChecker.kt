package info.igreque.keepmecontributingkt

import android.util.Log
import kotlinx.coroutines.runBlocking

class ContributionStatusChecker(private val onChanged: (CheckResult) -> Unit, private val gitHubClient: GitHubClient) {
    data class CheckResult(
        val target: CheckTarget,
        val contributionStatus: ContributionStatus
    )

    fun startPolling(target: CheckTarget) {
        Log.i("POLLING", "Start polling to GitHub")
        if (target.isFilled()) {
            onChanged(CheckResult(target, ContributionStatus.UNKNOWN))
            runBlocking {
                Log.d(
                    "GitHub",
                    gitHubClient.getLatestCommitDateString(
                        target.repositoryName.toString(),
                        target.contributorName.toString()
                    ).toString()
                )
            }
        }
    }
}