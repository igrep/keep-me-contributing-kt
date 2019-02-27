package info.igreque.keepmecontributingkt

import android.content.Context
import info.igreque.keepmecontributingkt.core.ContributionStatusChecker
import info.igreque.keepmecontributingkt.core.Env
import info.igreque.keepmecontributingkt.core.MyLogger
import info.igreque.keepmecontributingkt.core.Timestamp
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

    override val contributionStatusChecker =
        ContributionStatusChecker(this::handleCheckResult, gitHubClient) { this.getBeginningOfToday() }

    private fun getBeginningOfToday(): Timestamp = Calendar.getInstance(Locale("ja", "JP", "JP")).run {
        time = Date()
        val jst = TimeZone.getTimeZone("Asia/Tokyo")
        timeZone = jst
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
        time.time
    }
}