package info.igreque.keepmecontributingkt.browser

import info.igreque.keepmecontributingkt.core.ContributionStatus
import info.igreque.keepmecontributingkt.core.ContributionStatusChecker
import org.w3c.dom.Element

class BannerView(private val element: Element) : CheckResultView {
    override fun update(checkResult: ContributionStatusChecker.CheckResult) {
        val (elementClass, message) = when (checkResult.contributionStatus) {
            ContributionStatus.Unknown ->
                Pair("loading", "Loading ${checkResult.target.contributorName}'s contribution status...")
            ContributionStatus.NotYet ->
                Pair("danger", "Oh my... ${checkResult.target.contributorName} has NOT contributed yet today!")
            ContributionStatus.Done ->
                Pair("success", "Congratulations! ${checkResult.target.contributorName} has already contributed today!")
            ContributionStatus.DoneNoCheck ->
                Pair("success", "Congratulations! ${checkResult.target.contributorName} has already contributed today!")
            is ContributionStatus.Error ->
                Pair(
                    "warning",
                    "An error occurred while loading contribution status. See the developer console for details."
                )
        }
        element.className = elementClass
        element.textContent = message
    }
}