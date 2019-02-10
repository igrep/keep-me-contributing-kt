package info.igreque.keepmecontributingkt

data class CheckTarget(
    val contributorName: CharSequence,
    val repositoryName: CharSequence,
    val accessToken: CharSequence
) {
    fun isFilled(): Boolean =
        contributorName.isNotBlank() && repositoryName.isNotBlank() && accessToken.isNotBlank()
}