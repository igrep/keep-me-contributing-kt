import info.igreque.keepmecontributingkt.core.MyLogger
import kotlin.js.Date

object MyLoggerJs : MyLogger {
    override fun w(message: String) {
        console.warn(Date().toString(), message)
    }

    override fun i(message: String) {
        console.info(Date().toString(), message)
    }

    override fun e(message: String, err: Throwable) {
        console.error(Date().toString(), message, err)
    }
}