package info.igreque.keepmecontributingkt

class LaunchCheckerInteraction(
    view: ViewViaNotification,
    gitHubClient: GitHubClient,
    private val repository: CheckTargetRepository
) {
    private val model: ContributionStatusChecker = ContributionStatusChecker(view::show, gitHubClient)

    fun run() = model.startPolling(repository.load())

}
