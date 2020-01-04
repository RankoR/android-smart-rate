package com.g2pdev.smartrate.interactor.never_ask

import com.g2pdev.smartrate.repository.RateRepository
import io.reactivex.Completable

internal interface SetNeverAsk {
    fun exec(neverAsk: Boolean): Completable
}

internal class SetNeverAskImpl(
    private val rateRepository: RateRepository
) : SetNeverAsk {

    override fun exec(neverAsk: Boolean): Completable {
        return rateRepository.setNeverAsk(neverAsk)
    }
}