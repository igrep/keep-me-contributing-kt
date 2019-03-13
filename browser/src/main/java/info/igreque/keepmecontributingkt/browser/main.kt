import info.igreque.keepmecontributingkt.browser.CheckTargetRepositoryBrowser
import info.igreque.keepmecontributingkt.browser.GitHubClientJs
import info.igreque.keepmecontributingkt.browser.PeriodicalExecutor
import info.igreque.keepmecontributingkt.core.LaunchCheckerInteraction
import info.igreque.keepmecontributingkt.core.UpdateCheckTargetInteraction
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.w3c.dom.HTMLInputElement
import kotlin.browser.document

fun main() {
    GlobalScope.launch {
        val query = GitHubClientJs.getQuery("/dist/GetMaster.graphql")

        val inputContributorName = getInputElement("inputContributorName")
        val inputRepositoryName = getInputElement("inputRepositoryName")
        val inputAccessToken = getInputElement("inputAccessToken")

        val pe = PeriodicalExecutor {
            GlobalScope.launch {
                LaunchCheckerInteraction(EnvBrowser(inputAccessToken.value, query)).run()
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
                UpdateCheckTargetInteraction(EnvBrowser(accessToken, query)).run(
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