package info.igreque.keepmecontributingkt

import android.app.job.JobParameters
import android.app.job.JobService
import android.util.Log
import info.igreque.keepmecontributingkt.core.LaunchCheckerInteraction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ContributionStatusCheckerService : JobService(), CoroutineScope {
    private val job = Job()
    override val coroutineContext = Dispatchers.Main + job

    override fun onStartJob(params: JobParameters?): Boolean {
        launch {
            Log.i("INFO", "Beginning Job")
            LaunchCheckerInteraction(EnvAndroid(applicationContext)).run()
            jobFinished(params, false)
        }
        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        job.cancel()
        return true
    }
}
