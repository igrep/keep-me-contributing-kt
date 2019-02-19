package info.igreque.keepmecontributingkt

import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.fail
import java.util.*

@ExtendWith(MockKExtension::class)
class ContributionStatusCheckerTest {

    @Nested
    inner class StartPollingTest {
        private var lastCheckResult: ContributionStatusChecker.CheckResult? = null
        private val gitHubClient = mockk<GitHubClient>()
        private var currentTime: Date? = null
        private val subject = ContributionStatusChecker(this::writeResult, gitHubClient) {
            currentTime ?: fail("Date is not initialized!")
        }

        private val calendar = Calendar.getInstance(Locale("ja", "JP", "JP"))
        private val currentTimeHour = 12

        @BeforeEach
        fun setup() {
            clearMocks(gitHubClient)

            calendar.set(Calendar.HOUR_OF_DAY, currentTimeHour)
            currentTime = calendar.time

            lastCheckResult = null
        }

        /*
        & Run gitHubClient to check:
            - when gitHubClient returns Date of today, the result is DONE
            - when gitHubClient returns Date of yesterday, the result is NOT_YET
            - when gitHubClient throws an error, the result is ERROR (and is logged).
        When the last contributed time is null:
            * Run gitHubClient to check
        When the last contributed time is before the beginning of today:
            * Run gitHubClient to check
        When the last contributed time is after the beginning of today:
            - Don't run gitHubClient
        */

        @Nested
        inner class WhenLastContributedTimeIsNull {

            @Test
            fun whenGitHubClientReturnsTimeOfToday() {
                calendar.time = currentTime
                calendar.set(Calendar.HOUR_OF_DAY, currentTimeHour - 1)

                val returnedTime = calendar.time
                coEvery { gitHubClient.getLatestCommitDate("contributor", "repository") }.returns(returnedTime)

                val target = CheckTarget("repository", "contributor", "accessToken", null)
                subject.startPolling(target)

                assertThat(lastCheckResult).isEqualTo(
                    ContributionStatusChecker.CheckResult(target, ContributionStatus.DONE)
                )
            }
        }

        private fun writeResult(checkResult: ContributionStatusChecker.CheckResult) {
            lastCheckResult = checkResult
        }
    }
}