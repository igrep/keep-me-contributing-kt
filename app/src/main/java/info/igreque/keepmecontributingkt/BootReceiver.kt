package info.igreque.keepmecontributingkt

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, i: Intent) {
        Log.i("INFO", "Running BootReceiver")

        // NOTE: Check action to avoid UnsafeProtectedBroadcastReceiver
        if (Intent.ACTION_BOOT_COMPLETED == i.action) {
            ContributionStatusCheckerService.enqueueWork(context, i)
        }
    }

}
