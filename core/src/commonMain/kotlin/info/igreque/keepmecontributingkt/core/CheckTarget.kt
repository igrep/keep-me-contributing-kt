package info.igreque.keepmecontributingkt.core

data class CheckTarget(
    val contributorName: CharSequence,
    val repositoryName: CharSequence,
    val accessToken: CharSequence,
    val lastCommitTime: Timestamp?
) {
    fun isFormFilled(): Boolean =
        contributorName.isNotBlank() && repositoryName.isNotBlank() && accessToken.isNotBlank()

    fun updateLastCommitTime(lastCommitTime: Timestamp?) =
        CheckTarget(this.contributorName, this.repositoryName, this.accessToken, lastCommitTime)

    override fun toString() =
        "CheckTarget(contributorName=$contributorName, repositoryName=$repositoryName, accessToken=*****, lastCommitTime=$lastCommitTime)"
}