package info.igreque.keepmecontributingkt

import java.util.*

interface GitHubClient {
    suspend fun getLatestCommitDateString(repositoryName: String, contributorName: String): Date
}