import info.igreque.keepmecontributingkt.browser.GitHubClientJs
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.w3c.dom.HTMLInputElement
import kotlin.browser.document

fun main() {
    document.getElementById("formCheckTarget")!!.addEventListener("submit", {
        it.preventDefault()
        it.stopPropagation()

        val contributorName =
            (document.getElementById("inputContributorName") as HTMLInputElement).value
        val repositoryName =
            (document.getElementById("inputRepositoryName") as HTMLInputElement).value
        val accessToken =
            (document.getElementById("inputAccessToken") as HTMLInputElement).value

        GlobalScope.launch {
            val query = GitHubClientJs.getQuery("/dist/GetMaster.graphql")
            val latestCommitDate =
                GitHubClientJs(accessToken, query).getLatestCommitDate(contributorName, repositoryName)
            console.log("Latest commit date: $latestCommitDate")
        }

    })
}