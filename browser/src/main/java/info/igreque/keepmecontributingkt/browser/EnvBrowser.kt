import info.igreque.keepmecontributingkt.browser.CheckResultView
import info.igreque.keepmecontributingkt.browser.CheckTargetRepositoryBrowser
import info.igreque.keepmecontributingkt.browser.GitHubClientJs
import info.igreque.keepmecontributingkt.core.ContributionStatusChecker
import info.igreque.keepmecontributingkt.core.Env
import info.igreque.keepmecontributingkt.core.MyLogger
import info.igreque.keepmecontributingkt.core.Timestamp
import kotlin.js.Date

class EnvBrowser(
    accessToken: String,
    queryContent: String,
    private vararg val views: CheckResultView
) : Env {
    override val checkTargetRepository = CheckTargetRepositoryBrowser
    override val gitHubClient = GitHubClientJs(accessToken, queryContent)

    override val logger: MyLogger = MyLoggerJs

    override fun handleCheckResult(checkResult: ContributionStatusChecker.CheckResult) {
        for (view in views) {
            view.update(checkResult)
        }
        logger.logResult(checkResult)
        checkResult.target.lastCommitTime?.let { checkTargetRepository.updateLastCommitTime(it) }
    }

    override val contributionStatusChecker =
        ContributionStatusChecker(this::handleCheckResult, gitHubClient) { this.getBeginningOfToday() }

    private fun getBeginningOfToday(): Timestamp {
        val now = Date()
        return Date(now.getFullYear(), now.getMonth(), now.getDate(), 0, 0, 0, 0).getTime().toLong()
    }
}