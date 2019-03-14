package info.igreque.keepmecontributingkt.browser

import info.igreque.keepmecontributingkt.core.ContributionStatus
import info.igreque.keepmecontributingkt.core.ContributionStatusChecker
import org.w3c.dom.Element

class FaviconView(private val element: Element) : CheckResultView {
    override fun update(checkResult: ContributionStatusChecker.CheckResult) {
        val suffix = when (checkResult.contributionStatus) {
            ContributionStatus.Unknown ->
                "loading"
            ContributionStatus.NotYet ->
                "not_yet"
            ContributionStatus.Done ->
                "contributed"
            ContributionStatus.DoneNoCheck ->
                "contributed"
            is ContributionStatus.Error ->
                "error"
        }

        element.setAttribute("href", "img/favicon-$suffix.ico")
    }
}