package info.igreque.keepmecontributingkt.browser

import info.igreque.keepmecontributingkt.core.GitHubClient
import info.igreque.keepmecontributingkt.core.Timestamp
import org.w3c.fetch.Headers
import org.w3c.fetch.RequestInit
import kotlin.browser.window
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import kotlin.js.json

class GitHubClientJs(private val accessToken: String, private val queryContent: String) : GitHubClient {
    val headers = Headers().apply {
        append("Authorization", "bearer token: $accessToken")
        append("Content-Type", "application/json; charset=utf-8")
    }

    companion object {
        suspend fun getQuery(queryFileName: String): String = suspendCoroutine { cont ->
            window.fetch(queryFileName)
                .then { response -> response.text() }
                .then { body -> cont.resumeWith(Result.success(body)) }
                .catch { err -> cont.resumeWithException(err) }
        }
    }

    override suspend fun getLatestCommitDate(contributorName: String, repositoryName: String): Timestamp =
        suspendCoroutine { cont ->
            val rinit = RequestInit()
            rinit.headers = headers
            rinit.method = "POST"
            rinit.body = buildBody(contributorName, repositoryName, queryContent)
            window.fetch("https://api.github.com/graphql", rinit)
                .then { response -> response.json() }
                .then { body -> console.log(body); cont.resumeWith(Result.success(body as Timestamp)) }
                .catch { err -> cont.resumeWithException(err) }
        }

    private fun buildBody(contributorName: String, repositoryName: String, queryContent: String): String {
        val jsonString = json(
            Pair("query", queryContent),
            Pair("variables", json(Pair("contributorName", contributorName), Pair("repositoryName", repositoryName)))
        ).toString()
        console.log(jsonString)
        return jsonString
    }
}