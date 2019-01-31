package info.igreque.keepmecontributingkt

class LaunchCheckerInteraction(
    view: ViewViaNotification,
    private val repository: CheckTargetRepository
) {
    private val model: ContributionStatusChecker = ContributionStatusChecker(view::show)

    fun run() = model.startPolling(repository.load())

}
