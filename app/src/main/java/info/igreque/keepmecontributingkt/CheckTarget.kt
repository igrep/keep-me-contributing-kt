package info.igreque.keepmecontributingkt

import java.util.*

data class CheckTarget(
    val contributorName: CharSequence,
    val repositoryName: CharSequence,
    val accessToken: CharSequence,
    val lastCommitTime: Date?
) {
    fun isFormFilled(): Boolean =
        contributorName.isNotBlank() && repositoryName.isNotBlank() && accessToken.isNotBlank()

    fun updateLastCommitTime(lastCommitTime: Date?) =
        CheckTarget(this.contributorName, this.repositoryName, this.accessToken, lastCommitTime)

    override fun toString() =
        "CheckTarget(contributorName=$contributorName, repositoryName=$repositoryName, accessToken=*****, lastCommitTime=$lastCommitTime)"
}