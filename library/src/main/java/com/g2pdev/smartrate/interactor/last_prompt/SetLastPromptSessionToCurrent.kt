package com.g2pdev.smartrate.interactor.last_prompt

import com.g2pdev.smartrate.interactor.session_count.GetSessionCount
import com.g2pdev.smartrate.repository.RateRepository
import io.reactivex.Completable

internal interface SetLastPromptSessionToCurrent {
    fun exec(): Completable
}

internal class SetLastPromptSessionToCurrentImpl(
    private val rateRepository: RateRepository,
    private val getSessionCount: GetSessionCount
) : SetLastPromptSessionToCurrent {

    override fun exec(): Completable {
        return getSessionCount
            .exec()
            .flatMapCompletable(rateRepository::setLastPromptSession)
    }
}
