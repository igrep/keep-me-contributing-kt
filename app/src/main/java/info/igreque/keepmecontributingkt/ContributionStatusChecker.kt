package info.igreque.keepmecontributingkt

import android.util.Log

class ContributionStatusChecker(private val onChanged: (CheckResult) -> Unit) {
    data class CheckResult(
        val target: CheckTarget,
        val contributionStatus: ContributionStatus
    )

    fun startPolling(target: CheckTarget) {
        Log.i("POLLING", "Start polling to GitHub")
        if (target.isFilled()) {
            onChanged(CheckResult(target, ContributionStatus.UNKNOWN))
        }
    }
}