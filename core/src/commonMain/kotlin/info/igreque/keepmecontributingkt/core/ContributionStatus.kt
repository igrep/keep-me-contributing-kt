package info.igreque.keepmecontributingkt.core

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

    object DoneNoCheck : ContributionStatus() {
        override fun toString() = "ContributionStatus.DoneNoCheck"
    }

    data class Error(val exception: Throwable) : ContributionStatus() {
        override fun toString(): String =
            "ContributionStatus.Error($exception)"
    }
}
