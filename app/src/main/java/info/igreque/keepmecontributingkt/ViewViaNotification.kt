package info.igreque.keepmecontributingkt

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationCompat.GROUP_ALERT_SUMMARY

class ViewViaNotification(private val context: Context) {
    private val builder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL)
    private val notificationGroup = "Contribution Status"

    companion object {
        private const val NOTIFICATION_CHANNEL = "NOTIFICATION_CHANNEL"
        private const val NOTIFICATION_ID = 1

        fun createWithNotificationChannel(context: Context) =
            ViewViaNotification(context).apply { createNotificationChannel() }
    }

    // Copied from https://developer.android.com/training/notify-user/build-notification
    fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.getString(R.string.channel_name)
            val descriptionText = context.getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(NOTIFICATION_CHANNEL, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun show(result: ContributionStatusChecker.CheckResult) {
        builder.apply {
            setSmallIcon(R.mipmap.ic_launcher)
            setBadgeIconType(NotificationCompat.BADGE_ICON_NONE)
            setContentTitle("Have I contributed today?")
            setContentText(messageFromCheckResult(result))
            setOngoing(shouldBeSticky(result))
            setGroup(notificationGroup)
            setGroupAlertBehavior(GROUP_ALERT_SUMMARY)
            priority = NotificationCompat.PRIORITY_DEFAULT
        }

        val mNotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        mNotificationManager.notify(NOTIFICATION_ID, builder.build())
    }

    fun shouldBeSticky(result: ContributionStatusChecker.CheckResult): Boolean =
        result.contributionStatus == ContributionStatus.NotYet

    fun messageFromCheckResult(result: ContributionStatusChecker.CheckResult): CharSequence =
        when (result.contributionStatus) {
            ContributionStatus.Unknown ->
                "Checking the contributions by ${result.target.contributorName} to ${result.target.repositoryName}..."
            ContributionStatus.NotYet ->
                "Watch out! NO contributions by ${result.target.contributorName} to ${result.target.repositoryName} today!"
            ContributionStatus.Done ->
                "Yahoo! ${result.target.contributorName} has already contributed to ${result.target.repositoryName} today!"
            is ContributionStatus.Error ->
                "Error when checking the contributions by  ${result.target.contributorName} to ${result.target.repositoryName}! See LogCat."
        }
}