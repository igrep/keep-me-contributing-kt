import info.igreque.keepmecontributingkt.browser.*
import info.igreque.keepmecontributingkt.core.LaunchCheckerInteraction
import info.igreque.keepmecontributingkt.core.UpdateCheckTargetInteraction
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.w3c.dom.Element
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.get
import kotlin.browser.document

fun main() {
    GlobalScope.launch {
        val query = GitHubClientJs.getQuery("/dist/GetMaster.graphql")

        val inputContributorName = getInputElement("inputContributorName")
        val inputRepositoryName = getInputElement("inputRepositoryName")
        val inputAccessToken = getInputElement("inputAccessToken")

        val faviconElements = document.querySelectorAll("link[type=\"image/vnd.microsoft.icon\"]")
        val views: Array<CheckResultView> = arrayOf(
            FaviconView(faviconElements[0] as Element),
            FaviconView(faviconElements[1] as Element)
        )

        val pe = PeriodicalExecutor {
            GlobalScope.launch {
                LaunchCheckerInteraction(EnvBrowser(inputAccessToken.value, query, *views)).run()
            }
        }

        val target = CheckTargetRepositoryBrowser.load()
        inputContributorName.value = target.contributorName.toString()
        inputRepositoryName.value = target.repositoryName.toString()
        inputAccessToken.value = target.accessToken.toString()

        document.getElementById("formCheckTarget")!!.addEventListener("submit", {
            it.preventDefault()
            it.stopPropagation()

            val accessToken = inputAccessToken.value
            GlobalScope.launch {
                UpdateCheckTargetInteraction(EnvBrowser(accessToken, query, *views)).run(
                    inputContributorName.value,
                    inputRepositoryName.value,
                    accessToken
                )
            }
            pe.restart()
        })
    }
}

private fun getInputElement(elementId: String) = document.getElementById(elementId) as HTMLInputElement