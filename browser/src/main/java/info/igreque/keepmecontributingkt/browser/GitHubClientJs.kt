package info.igreque.keepmecontributingkt.browser

import info.igreque.keepmecontributingkt.core.GitHubClient
import info.igreque.keepmecontributingkt.core.Timestamp
import org.w3c.fetch.Headers
import org.w3c.fetch.RequestInit
import kotlin.browser.window
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import kotlin.js.*

class GitHubClientJs(private val accessToken: String, private val queryContent: String) : GitHubClient {
    private val headers = Headers().apply {
        append("Authorization", "bearer $accessToken")
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
            rinit.body = buildRequestBody(contributorName, repositoryName, queryContent)
            window.fetch("https://api.github.com/graphql", rinit)
                .then { response -> response.json() }
                .then { body: dynamic ->
                    console.log(body as Any)
                    val dateStr =
                        body.data.repository.ref.target.history.nodes[0].committedDate as String
                    cont.resumeWith(Result.success(Date(dateStr).getTime().toLong()))
                }
                .catch { err -> cont.resumeWithException(err) }
        }

    private fun buildRequestBody(contributorName: String, repositoryName: String, queryContent: String): String =
        JSON.stringify(
            json(
                Pair("query", queryContent),
                Pair(
                    "variables",
                    json(Pair("contributorName", contributorName), Pair("repositoryName", repositoryName))
                )
            )
        )
}