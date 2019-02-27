package info.igreque.keepmecontributingkt

import android.util.Log
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.response.CustomTypeAdapter
import com.apollographql.apollo.response.CustomTypeValue
import info.igreque.keepmecontributingkt.core.GitHubClient
import info.igreque.keepmecontributingkt.core.Timestamp
import info.igreque.keepmecontributingkt.type.CustomType
import okhttp3.OkHttpClient
import java.text.SimpleDateFormat
import java.util.*
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class GitHubClientAndroid(private val accessToken: String) : GitHubClient {
    private val okHttpClient = OkHttpClient.Builder().run {
        authenticator { _, response ->
            response
                .request()
                .newBuilder()
                .addHeader("Authorization", "Bearer $accessToken")
                .build()
        }.build()
    } ?: throw RuntimeException("Failed to create an OkHttpClient!")

    private val dateFormat: SimpleDateFormat =
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.JAPAN).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }

    private val dateCustomTypeAdapter = object : CustomTypeAdapter<Date> {
        override fun encode(value: Date): CustomTypeValue<*> =
            CustomTypeValue.GraphQLString(dateFormat.format(value))

        override fun decode(value: CustomTypeValue<*>): Date =
            dateFormat.parse(value.value.toString())
    }

    override suspend fun getLatestCommitDate(contributorName: String, repositoryName: String): Timestamp =
        suspendCoroutine { cont ->
            ApolloClient.builder().run {
                okHttpClient(okHttpClient)
                serverUrl("https://api.github.com/graphql")
                addCustomTypeAdapter(CustomType.DATETIME, dateCustomTypeAdapter)
                build()
            }.query(GetMasterQuery(contributorName, repositoryName)).enqueue(
                object : ApolloCall.Callback<GetMasterQuery.Data>() {
                    override fun onResponse(response: Response<GetMasterQuery.Data>) {
                        val target =
                            response.data()?.repository()?.ref()?.target()
                        if (target !is GetMasterQuery.AsCommit) {
                            Log.w("GraphQL", "Assertion failure: target is not a commit: '${response.data()}'")
                            logErrors(response)
                            return
                        }
                        val nodes = target.history().nodes
                        if (nodes == null) {
                            Log.w("GraphQL", "Assertion failure: Wrong response (nodes is null): '${response.data()}'")
                            logErrors(response)
                            return
                        }
                        if (nodes.isEmpty()) {
                            Log.w("GraphQL", "Empty nodes returned by GitHub")
                            return
                        }
                        nodes.forEach {
                            cont.resumeWith(Result.success(it.committedDate().time))
                        }
                    }

                    override fun onFailure(e: ApolloException) {
                        cont.resumeWithException(e)
                    }
                }
            )

        }

    private fun logErrors(response: Response<*>) {
        for (err in response.errors()) {
            Log.w("GraphQL", err.toString())
        }
    }
}