import info.igreque.keepmecontributingkt.browser.CheckTargetRepositoryBrowser
import info.igreque.keepmecontributingkt.browser.GitHubClientJs
import info.igreque.keepmecontributingkt.core.UpdateCheckTargetInteraction
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.w3c.dom.HTMLInputElement
import kotlin.browser.document

fun main() {
    val inputContributorName = getInputElement("inputContributorName")
    val inputRepositoryName = getInputElement("inputRepositoryName")
    val inputAccessToken = getInputElement("inputAccessToken")

    val target = CheckTargetRepositoryBrowser.load()
    inputContributorName.value = target.contributorName.toString()
    inputRepositoryName.value = target.repositoryName.toString()
    inputAccessToken.value = target.accessToken.toString()

    document.getElementById("formCheckTarget")!!.addEventListener("submit", {
        it.preventDefault()
        it.stopPropagation()

        val contributorName = inputContributorName.value
        val repositoryName = inputRepositoryName.value
        val accessToken = inputAccessToken.value

        GlobalScope.launch {
            val query = GitHubClientJs.getQuery("/dist/GetMaster.graphql")
            UpdateCheckTargetInteraction(EnvBrowser(accessToken, query)).run(
                contributorName,
                repositoryName,
                accessToken
            )
        }
    })
}

private fun getInputElement(elementId: String) = document.getElementById(elementId) as HTMLInputElement