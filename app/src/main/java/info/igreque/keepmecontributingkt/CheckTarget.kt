package info.igreque.keepmecontributingkt

data class CheckTarget(val contributorName: CharSequence, val repositoryName: CharSequence) {
    fun isFilled(): Boolean =
        contributorName.isNotBlank() && repositoryName.isNotBlank()
}