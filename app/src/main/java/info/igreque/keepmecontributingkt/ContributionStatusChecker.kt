package info.igreque.keepmecontributingkt

class ContributionStatusChecker(private val onChanged: (CheckResult) -> Unit) {
    data class CheckResult(
        val target: CheckTarget,
        val contributionStatus: ContributionStatus
    )

    fun startPolling(target: CheckTarget) {
        if (target.isFilled()) {
            onChanged(CheckResult(target, ContributionStatus.UNKNOWN))
        }
    }
}