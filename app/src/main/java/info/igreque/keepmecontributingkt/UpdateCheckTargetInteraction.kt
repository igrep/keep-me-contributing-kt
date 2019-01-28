package info.igreque.keepmecontributingkt

class UpdateCheckTargetInteraction(
    view: ViewViaNotification,
    private val repository: CheckTargetRepository
) {
    private val model: ContributionStatusChecker = ContributionStatusChecker(view::show)

    fun run(userName: CharSequence, repositoryName: CharSequence) {
        val target = CheckTarget(userName, repositoryName)
        model.startPolling(target)
        repository.save(target)
    }
}