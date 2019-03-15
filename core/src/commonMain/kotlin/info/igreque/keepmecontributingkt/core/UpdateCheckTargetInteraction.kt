package info.igreque.keepmecontributingkt.core

class UpdateCheckTargetInteraction(private val env: Env) {
    suspend fun run(userName: CharSequence, repositoryName: CharSequence, accessToken: CharSequence) {
        val target = CheckTarget(userName, repositoryName, accessToken, null)
        env.checkTargetRepository.save(target)
        env.contributionStatusChecker.doCheck(target)
    }
}