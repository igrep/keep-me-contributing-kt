package info.igreque.keepmecontributingkt.browser

import info.igreque.keepmecontributingkt.core.ContributionStatusChecker

interface CheckResultView {
    fun update(checkResult: ContributionStatusChecker.CheckResult)
}