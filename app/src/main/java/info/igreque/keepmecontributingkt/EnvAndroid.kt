package info.igreque.keepmecontributingkt

import android.content.Context
import java.util.*

class EnvAndroid(applicationContext: Context, accessToken: String? = null) : Env {
    override val checkTargetRepository = CheckTargetRepositoryAndroid(applicationContext)
    override val gitHubClient =
        GitHubClientAndroid(
            accessToken ?: checkTargetRepository.load().accessToken.toString()
        )

    override val logger: MyLogger = MyLoggerAndroid

    private val viewViaNotification = ViewViaNotification.createWithNotificationChannel(applicationContext)
    override fun handleCheckResult(checkResult: ContributionStatusChecker.CheckResult) {
        viewViaNotification.show(checkResult)
        logger.logResult(checkResult)
        checkResult.target.lastCommitTime?.let { checkTargetRepository.updateLastCommitTime(it) }
    }

    override val contributionStatusChecker = ContributionStatusChecker(this::handleCheckResult, gitHubClient) { Date() }
}