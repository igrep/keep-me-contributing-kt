package info.igreque.keepmecontributingkt

class LaunchCheckerInteraction(
    private val env: Env
) {
    suspend fun run() = env.contributionStatusChecker.doCheck(env.checkTargetRepository.load())
}
