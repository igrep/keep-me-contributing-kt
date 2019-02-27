package info.igreque.keepmecontributingkt.core

interface GitHubClient {
    suspend fun getLatestCommitDate(contributorName: String, repositoryName: String): Timestamp
}