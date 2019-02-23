package info.igreque.keepmecontributingkt

interface Env {
    val checkTargetRepository: CheckTargetRepository
    val gitHubClient: GitHubClient
    val logger: MyLogger
    fun handleCheckResult(checkResult: ContributionStatusChecker.CheckResult)
    val contributionStatusChecker: ContributionStatusChecker
}