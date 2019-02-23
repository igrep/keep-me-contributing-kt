package info.igreque.keepmecontributingkt

import java.util.*

interface CheckTargetRepository {
    fun save(target: CheckTarget)
    fun updateLastCommitTime(lastCommitTime: Date)
    fun load(): CheckTarget
}