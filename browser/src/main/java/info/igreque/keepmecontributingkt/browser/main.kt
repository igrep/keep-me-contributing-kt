import info.igreque.keepmecontributingkt.browser.GitHubClientJs
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.browser.window

fun main() {
    GlobalScope.launch {
        val query = GitHubClientJs.getQuery("/dist/GetMaster.graphql")
        val accessToken = window.prompt("GitHub Access token") ?: ""
        val latestCommitDate = GitHubClientJs(accessToken, query)
            .getLatestCommitDate(
                "igrep",
                "daily-commits"
            )
        console.log("Latest commit date: $latestCommitDate")
    }
}