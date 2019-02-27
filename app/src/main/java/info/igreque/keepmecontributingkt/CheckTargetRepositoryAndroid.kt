package info.igreque.keepmecontributingkt

import android.content.Context
import info.igreque.keepmecontributingkt.core.CheckTarget
import info.igreque.keepmecontributingkt.core.CheckTargetRepository
import info.igreque.keepmecontributingkt.core.Timestamp

class CheckTargetRepositoryAndroid(private val context: Context) : CheckTargetRepository {
    private val fileName = "pref"

    private val keyContributorName = "contributorName"
    private val keyRepositoryName = "repositoryName"
    private val keyAccessToken = "accessToken"
    private val keyLastCommitTime = "lastCommitTime"
    private val nullTime = 0L

    override fun save(target: CheckTarget) {
        val editor = context.getSharedPreferences(fileName, Context.MODE_PRIVATE).edit()
        editor.putString(keyContributorName, target.contributorName.toString())
        editor.putString(keyRepositoryName, target.repositoryName.toString())
        editor.putString(keyAccessToken, target.accessToken.toString())
        editor.putLong(keyLastCommitTime, (target.lastCommitTime ?: nullTime))
        editor.apply()
    }

    override fun updateLastCommitTime(lastCommitTime: Timestamp) {
        val editor = context.getSharedPreferences(fileName, Context.MODE_PRIVATE).edit()
        editor.putLong(keyLastCommitTime, lastCommitTime)
        editor.apply()
    }

    override fun load(): CheckTarget {
        val pref = context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
        val lastCommitTime = pref.getLong(keyLastCommitTime, nullTime)
        return CheckTarget(
            pref.getString(keyContributorName, "") ?: "",
            pref.getString(keyRepositoryName, "") ?: "",
            pref.getString(keyAccessToken, "") ?: "",
            if (lastCommitTime == nullTime) {
                null
            } else {
                lastCommitTime
            }
        )
    }
}