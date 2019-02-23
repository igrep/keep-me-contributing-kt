package info.igreque.keepmecontributingkt

sealed class ContributionStatus {
    object Unknown : ContributionStatus() {
        override fun toString() = "ContributionStatus.Unknown"
    }

    object NotYet : ContributionStatus() {
        override fun toString() = "ContributionStatus.NotYet"
    }

    object Done : ContributionStatus() {
        override fun toString() = "ContributionStatus.Done"
    }

    data class Error(val exception: Exception) : ContributionStatus() {
        override fun toString(): String =
            "ContributionStatus.Error($exception)"
    }
}
