package info.igreque.keepmecontributingkt.browser

import info.igreque.keepmecontributingkt.core.CheckTarget
import info.igreque.keepmecontributingkt.core.CheckTargetRepository
import info.igreque.keepmecontributingkt.core.Timestamp
import kotlin.browser.localStorage

object CheckTargetRepositoryBrowser : CheckTargetRepository {
    private const val keyContributorName = "contributorName"
    private const val keyRepositoryName = "repositoryName"
    private const val keyAccessToken = "accessToken"
    private const val keyLastCommitTime = "lastCommitTime"

    override fun save(target: CheckTarget) {
        localStorage.setItem(keyContributorName, target.contributorName.toString())
        localStorage.setItem(keyRepositoryName, target.repositoryName.toString())
        localStorage.setItem(keyAccessToken, target.accessToken.toString())
        if (target.lastCommitTime != null) {
            localStorage.setItem(keyLastCommitTime, target.lastCommitTime.toString())
        }
    }

    override fun updateLastCommitTime(lastCommitTime: Timestamp) {
        localStorage.setItem(keyLastCommitTime, lastCommitTime.toString())
    }

    override fun load(): CheckTarget =
        CheckTarget(
            localStorage.getItem(keyContributorName) ?: "",
            localStorage.getItem(keyRepositoryName) ?: "",
            localStorage.getItem(keyAccessToken) ?: "",
            localStorage.getItem(keyLastCommitTime)?.toLong()
        )
}