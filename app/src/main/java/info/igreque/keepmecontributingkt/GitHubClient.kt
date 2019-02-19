package info.igreque.keepmecontributingkt

import java.util.*

interface GitHubClient {
    suspend fun getLatestCommitDate(contributorName: String, repositoryName: String): Date
}