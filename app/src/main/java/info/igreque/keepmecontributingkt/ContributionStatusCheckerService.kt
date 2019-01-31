package info.igreque.keepmecontributingkt

import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_BOOT_COMPLETED
import android.support.v4.app.JobIntentService
import android.util.Log

class ContributionStatusCheckerService : JobIntentService() {
    companion object {
        fun enqueueWork(context: Context, i: Intent) {
            enqueueWork(context, ContributionStatusCheckerService::class.java, JOB_ID, i)
        }

        private const val JOB_ID = 1000
        const val EXTRA_CONTRIBUTOR_NAME = "info.igreque.keepmecontributingkt.contributorName"
        const val EXTRA_REPOSITORY_NAME = "info.igreque.keepmecontributingkt.repositoryName"
        const val ACTION_UPDATE_PREFERENCES_DONE = "info.igreque.keepmecontributingkt.updatePreferencesDone"
    }

    override fun onHandleWork(i: Intent) {
        Log.i("INFO", "Beginning onHandleWork")

        val view = ViewViaNotification.createWithNotificationChannel(applicationContext)
        val repository = CheckTargetRepository(applicationContext)

        when (i.action) {
            ACTION_UPDATE_PREFERENCES_DONE ->
                UpdateCheckTargetInteraction(view, repository).run(
                    i.getStringExtra(EXTRA_CONTRIBUTOR_NAME),
                    i.getStringExtra(EXTRA_REPOSITORY_NAME)
                )
            ACTION_BOOT_COMPLETED ->
                LaunchCheckerInteraction(view, repository).run()
            else ->
                Log.w("onHandleWork", "Unexpected action ${i.action}")
        }
    }
}
