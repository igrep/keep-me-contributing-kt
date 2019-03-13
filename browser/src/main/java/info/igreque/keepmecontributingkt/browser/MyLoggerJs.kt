import info.igreque.keepmecontributingkt.core.MyLogger

object MyLoggerJs : MyLogger {
    override fun w(message: String) {
        console.warn(message)
    }

    override fun i(message: String) {
        console.info(message)
    }

    override fun e(message: String, err: Exception) {
        console.error(message, err)
    }
}