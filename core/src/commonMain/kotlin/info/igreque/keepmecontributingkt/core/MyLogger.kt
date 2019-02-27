package info.igreque.keepmecontributingkt.core

interface MyLogger {
    fun e(message: String, err: Exception)
    fun w(message: String)
    fun i(message: String)

    fun logResult(result: ContributionStatusChecker.CheckResult) {
        if (result.contributionStatus is ContributionStatus.Error) {
            this.e(result.toString(), result.contributionStatus.exception)
            return
        }
        this.i(result.toString())
    }
}
