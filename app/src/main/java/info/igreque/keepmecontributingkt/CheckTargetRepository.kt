package info.igreque.keepmecontributingkt

import android.content.Context

class CheckTargetRepository(private val context: Context) {
    private val fileName = "pref"

    private val keyContributor = "contributor"
    private val keyRepositoryName = "repositoryName"

    fun save(target: CheckTarget) {
        val editor = context.getSharedPreferences(fileName, Context.MODE_PRIVATE).edit()
        editor.putString(keyContributor, target.contributor.toString())
        editor.putString(keyRepositoryName, target.repositoryName.toString())
        editor.apply()
    }

    fun load(): CheckTarget {
        val pref = context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
        return CheckTarget(
            pref.getString(keyContributor, "") ?: "",
            pref.getString(keyRepositoryName, "") ?: ""
        )
    }
}