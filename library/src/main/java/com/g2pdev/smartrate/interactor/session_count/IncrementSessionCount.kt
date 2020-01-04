package com.g2pdev.smartrate.interactor.session_count

import com.g2pdev.smartrate.repository.RateRepository
import io.reactivex.Completable

internal interface IncrementSessionCount {
    fun exec(): Completable
}

internal class IncrementSessionCountImpl(
    private val rateRepository: RateRepository,
    private val getSessionCount: GetSessionCount
) : IncrementSessionCount {

    override fun exec(): Completable {
        return getSessionCount
            .exec()
            .map { it + 1 }
            .flatMapCompletable(rateRepository::setSessionCount)
    }
}