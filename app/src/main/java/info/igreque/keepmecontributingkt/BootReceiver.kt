package info.igreque.keepmecontributingkt

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, i: Intent) {
        Log.i("BOOT", "Running BootReceiver")
        // NOTE: Check action to avoid UnsafeProtectedBroadcastReceiver warning
        if (Intent.ACTION_BOOT_COMPLETED == i.action) {
            Scheduler(context).schedule(ContributionStatusCheckerService::class.java)
        }
    }
}
