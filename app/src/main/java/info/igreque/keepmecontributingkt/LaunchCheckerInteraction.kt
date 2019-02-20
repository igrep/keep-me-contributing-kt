package info.igreque.keepmecontributingkt

import java.util.*

class LaunchCheckerInteraction(
    view: ViewViaNotification,
    gitHubClient: GitHubClient,
    private val repository: CheckTargetRepository
) {
    // TODO: Log and save the result
    private val model: ContributionStatusChecker = ContributionStatusChecker(view::show, gitHubClient) { Date() }

    fun run() = model.doCheck(repository.load())
}
