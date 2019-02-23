package info.igreque.keepmecontributingkt

import android.app.job.JobParameters
import android.app.job.JobService
import android.util.Log

class ContributionStatusCheckerService : JobService() {
    override fun onStartJob(params: JobParameters?): Boolean {
        Thread {
            Log.i("INFO", "Beginning Job")
            LaunchCheckerInteraction(EnvAndroid(applicationContext)).run()
            jobFinished(params, false)
        }.start()
        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean = true
}
