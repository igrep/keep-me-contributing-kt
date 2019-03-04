import info.igreque.keepmecontributingkt.browser.GitHubClientJs
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

fun main() {
    GlobalScope.launch {
        val query = GitHubClientJs.getQuery("/dist/GetMaster.graphql")
        val latestCommitDate = GitHubClientJs("", query)
            .getLatestCommitDate(
                "igrep",
                "daily-commits"
            )
        console.log("Latest commit date: $latestCommitDate")
    }
}