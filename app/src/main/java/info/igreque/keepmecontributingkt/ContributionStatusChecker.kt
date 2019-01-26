package info.igreque.keepmecontributingkt

class ContributionStatusChecker(private val onChanged: (CheckResult) -> Unit) {
    data class CheckResult(
        val contributor: CharSequence,
        val repositoryUrl: CharSequence,
        val contributionStatus: ContributionStatus
    )

    fun startPolling(contributor: CharSequence, repositoryUrl: CharSequence) {
        onChanged(CheckResult(contributor, repositoryUrl, ContributionStatus.UNKNOWN))
    }
}