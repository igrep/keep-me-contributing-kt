package info.igreque.keepmecontributingkt

class UpdateCheckTargetInteraction(private val env: Env) {
    suspend fun run(userName: CharSequence, repositoryName: CharSequence, accessToken: CharSequence) {
        val target = CheckTarget(userName, repositoryName, accessToken, null)
        env.contributionStatusChecker.doCheck(target)
        env.checkTargetRepository.save(target)
    }
}