package info.igreque.keepmecontributingkt

import info.igreque.keepmecontributingkt.core.*
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*

@ExtendWith(MockKExtension::class)
class ContributionStatusCheckerTest {

    @Nested
    inner class StartPollingTest {
        private var lastCheckResult: ContributionStatusChecker.CheckResult? = null
        private val gitHubClient = mockk<GitHubClient>()
        private val subject = ContributionStatusChecker(this::writeResult, gitHubClient) {
            calendar.run {
                set(Calendar.HOUR_OF_DAY, currentTimeHour)
                set(Calendar.DAY_OF_MONTH, currentTimeDayOfMonth)
                val currentTime = calendar.time.time

                time = Date(currentTime)
                val jst = TimeZone.getTimeZone("Asia/Tokyo")
                timeZone = jst
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
                time.time
            }
        }

        private val calendar = Calendar.getInstance(Locale("ja", "JP", "JP"))
        private val currentTimeDayOfMonth = 2
        private val currentTimeHour = 12

        @BeforeEach
        fun setup() {
            clearMocks(gitHubClient)
            lastCheckResult = null
        }

        @Nested
        inner class WhenLastContributedTimeIsNull {

            @Test
            fun whenGitHubClientReturnsTimeOfToday() {
                calendar.set(Calendar.HOUR_OF_DAY, currentTimeHour - 1)
                val returnedTime = calendar.time.time
                coEvery { gitHubClient.getLatestCommitDate("contributor", "repository") }.returns(returnedTime)

                shouldCheckWithFinalResult(ContributionStatus.Done, returnedTime)
            }

            @Test
            fun whenGitHubClientReturnsTimeOfYesterday() {
                calendar.set(Calendar.DAY_OF_MONTH, currentTimeDayOfMonth - 1)
                val returnedTime = calendar.time.time
                coEvery { gitHubClient.getLatestCommitDate("contributor", "repository") }.returns(returnedTime)

                shouldCheckWithFinalResult(ContributionStatus.NotYet, returnedTime)
            }

            @Test
            fun whenGitHubClientThrowsException() {
                val exception = RuntimeException("Test error")
                coEvery { gitHubClient.getLatestCommitDate("contributor", "repository") }.throws(exception)

                shouldCheckWithFinalResult(ContributionStatus.Error(exception), null)
            }

            private fun shouldCheckWithFinalResult(expectedStatus: ContributionStatus, latestCommitTime: Timestamp?) {
                val target = CheckTarget("contributor", "repository", "accessToken", null)
                runBlocking { subject.doCheck(target) }

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
                val targetTime = calendar.time.time
                val target = CheckTarget("repository", "contributor", "accessToken", targetTime)
                runBlocking { subject.doCheck(target) }
                assertThat(lastCheckResult?.contributionStatus).isEqualTo(ContributionStatus.DoneNoCheck)
                assertThat(lastCheckResult?.target?.lastCommitTime).isEqualTo(targetTime)
            }
        }

        private fun writeResult(checkResult: ContributionStatusChecker.CheckResult) {
            lastCheckResult = checkResult
        }
    }
}