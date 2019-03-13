package info.igreque.keepmecontributingkt.browser

import kotlin.browser.window

class PeriodicalExecutor(private val func: (Unit) -> Unit) {
    private val interval = 5 * 60 * 1000
    //private val interval = 5 * 1000
    private var tid = window.setInterval(func, interval)

    fun restart() {
        window.clearInterval(tid)
        tid = window.setInterval(func, interval)
    }
}