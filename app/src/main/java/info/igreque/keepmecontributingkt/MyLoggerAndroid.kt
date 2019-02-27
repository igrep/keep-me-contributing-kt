package info.igreque.keepmecontributingkt

import android.util.Log
import info.igreque.keepmecontributingkt.core.MyLogger

object MyLoggerAndroid : MyLogger {
    override fun e(message: String, err: Exception) {
        Log.e("MyLog", message, err)
    }

    override fun w(message: String) {
        Log.w("MyLog", message)
    }

    override fun i(message: String) {
        Log.i("MyLog", message)
    }
}