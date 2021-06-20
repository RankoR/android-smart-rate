package com.g2pdev.smartrate.interactor.never_ask

import com.g2pdev.smartrate.repository.RateRepository

internal interface IsNeverAsk {
    fun exec(): Boolean
}

internal class IsNeverAskImpl(
    private val rateRepository: RateRepository
) : IsNeverAsk {

    override fun exec(): Boolean {
        return rateRepository.isNeverAsk()
    }
}
