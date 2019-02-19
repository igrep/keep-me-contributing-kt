package info.igreque.keepmecontributingkt

import android.app.job.JobParameters
import android.app.job.JobService
import android.util.Log

class ContributionStatusCheckerService : JobService() {

    override fun onStartJob(params: JobParameters?): Boolean {
        Thread {
            val view =
                ViewViaNotification.createWithNotificationChannel(applicationContext)
            val repository = CheckTargetRepository(applicationContext)
            val gitHubClient = GitHubClientImpl(repository.load().accessToken.toString())
            Log.i("INFO", "Beginning Job")
            LaunchCheckerInteraction(view, gitHubClient, repository).run()
        }.start()
        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean = true
}