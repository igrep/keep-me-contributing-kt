package info.igreque.keepmecontributingkt

class UpdateCheckTargetInteraction(
    view: ViewViaNotification,
    gitHubClient: GitHubClient,
    private val repository: CheckTargetRepository
) {
    private val model: ContributionStatusChecker = ContributionStatusChecker(view::show, gitHubClient)

    fun run(userName: CharSequence, repositoryName: CharSequence, accessToken: CharSequence) {
        val target = CheckTarget(userName, repositoryName, accessToken)
        model.startPolling(target)
        repository.save(target)
    }
}