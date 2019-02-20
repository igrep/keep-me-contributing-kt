package info.igreque.keepmecontributingkt

import java.util.*

class UpdateCheckTargetInteraction(
    view: ViewViaNotification,
    gitHubClient: GitHubClient,
    private val repository: CheckTargetRepository
) {
    // TODO: Log and save the result
    private val model: ContributionStatusChecker = ContributionStatusChecker(view::show, gitHubClient) { Date() }

    fun run(userName: CharSequence, repositoryName: CharSequence, accessToken: CharSequence) {
        val target = CheckTarget(userName, repositoryName, accessToken, null)
        model.doCheck(target)
        repository.save(target)
    }
}