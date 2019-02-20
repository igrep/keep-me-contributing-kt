package info.igreque.keepmecontributingkt

sealed class ContributionStatus {
    object Unknown : ContributionStatus()
    object NotYet : ContributionStatus()
    object Done : ContributionStatus()
    data class Error(val exception: Exception) : ContributionStatus()
}
