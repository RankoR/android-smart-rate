package com.g2pdev.smartrate.interactor.never_ask

import com.g2pdev.smartrate.repository.RateRepository
import io.reactivex.Single

internal interface IsNeverAsk {
    fun exec(): Single<Boolean>
}

internal class IsNeverAskImpl(
    private val rateRepository: RateRepository
) : IsNeverAsk {

    override fun exec(): Single<Boolean> {
        return rateRepository.isNeverAsk()
    }
}