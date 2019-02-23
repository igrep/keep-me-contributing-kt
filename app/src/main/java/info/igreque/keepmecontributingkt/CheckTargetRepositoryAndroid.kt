package info.igreque.keepmecontributingkt

import android.content.Context
import java.util.*

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
        editor.putLong(keyLastCommitTime, (target.lastCommitTime?.time ?: nullTime))
        editor.apply()
    }

    override fun updateLastCommitTime(lastCommitTime: Date) {
        val editor = context.getSharedPreferences(fileName, Context.MODE_PRIVATE).edit()
        editor.putLong(keyLastCommitTime, lastCommitTime.time)
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
                Date(lastCommitTime)
            }
        )
    }
}