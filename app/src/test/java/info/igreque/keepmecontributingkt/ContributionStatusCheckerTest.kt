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
        private val currentTimeDayOfMonth = 2
        private val currentTimeHour = 12

        @BeforeEach
        fun setup() {
            clearMocks(gitHubClient)

            calendar.set(Calendar.HOUR_OF_DAY, currentTimeHour)
            calendar.set(Calendar.DAY_OF_MONTH, currentTimeDayOfMonth)
            currentTime = calendar.time

            lastCheckResult = null
        }

        @Nested
        inner class WhenLastContributedTimeIsNull {

            @Test
            fun whenGitHubClientReturnsTimeOfToday() {
                calendar.set(Calendar.HOUR_OF_DAY, currentTimeHour - 1)
                val returnedTime = calendar.time
                coEvery { gitHubClient.getLatestCommitDate("contributor", "repository") }.returns(returnedTime)

                shouldCheckWithFinalResult(ContributionStatus.Done, returnedTime)
            }

            @Test
            fun whenGitHubClientReturnsTimeOfYesterday() {
                calendar.set(Calendar.DAY_OF_MONTH, currentTimeDayOfMonth - 1)
                val returnedTime = calendar.time
                coEvery { gitHubClient.getLatestCommitDate("contributor", "repository") }.returns(returnedTime)

                shouldCheckWithFinalResult(ContributionStatus.NotYet, returnedTime)
            }

            @Test
            fun whenGitHubClientThrowsException() {
                val exception = RuntimeException("Test error")
                coEvery { gitHubClient.getLatestCommitDate("contributor", "repository") }.throws(exception)

                shouldCheckWithFinalResult(ContributionStatus.Error(exception), null)
            }

            private fun shouldCheckWithFinalResult(expectedStatus: ContributionStatus, latestCommitTime: Date?) {
                val target = CheckTarget("repository", "contributor", "accessToken", null)
                subject.startPolling(target)

                assertThat(lastCheckResult?.contributionStatus).isEqualTo(expectedStatus)
                assertThat(lastCheckResult?.target?.lastCommitTime).isEqualTo(latestCommitTime)
            }
        }

        @Nested
        inner class WhenLastContributedTimeIsAfterBeginningOfToday {
            @Test
            fun shouldNeverCallOnChanged() {
                calendar.set(Calendar.HOUR_OF_DAY, currentTimeHour - 1)
                calendar.set(Calendar.DAY_OF_MONTH, currentTimeDayOfMonth)
                val targetTime = calendar.time
                val target = CheckTarget("repository", "contributor", "accessToken", targetTime)
                subject.startPolling(target)
                assertThat(lastCheckResult).isNull()
            }
        }

        private fun writeResult(checkResult: ContributionStatusChecker.CheckResult) {
            lastCheckResult = checkResult
        }
    }
}