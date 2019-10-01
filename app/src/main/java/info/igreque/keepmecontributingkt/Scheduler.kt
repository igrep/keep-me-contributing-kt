package info.igreque.keepmecontributingkt

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.app.job.JobService
import android.content.ComponentName
import android.content.Context
import android.os.Build

class Scheduler(private val context: Context) {
    private val jobId = 1
    private val scheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler

    private val estimatedDownloadBytes: Long = 1700
    private val estimatedUploadBytes: Long = 1500

    fun <T : JobService> schedule(klass: Class<T>) {
        val jobInfo = JobInfo.Builder(jobId, ComponentName(context, klass))
            .apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    setEstimatedNetworkBytes(estimatedDownloadBytes, estimatedUploadBytes)
                    setImportantWhileForeground(false)
                }
                setRequiresDeviceIdle(false)
                setPersisted(false)
                setPeriodic(5 * 60 * 1000) // 5 min. But actually forced to be 15 min by Android.
                setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                setRequiresCharging(false)
            }.build()
        scheduler.schedule(jobInfo)
    }

    fun cancel() = scheduler.cancel(jobId)
}