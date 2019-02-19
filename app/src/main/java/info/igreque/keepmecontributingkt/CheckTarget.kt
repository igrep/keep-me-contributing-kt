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
}