package info.igreque.keepmecontributingkt.core

interface CheckTargetRepository {
    fun save(target: CheckTarget)
    fun updateLastCommitTime(lastCommitTime: Timestamp)
    fun load(): CheckTarget
}