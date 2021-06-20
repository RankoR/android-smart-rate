package com.g2pdev.smartrate.domain.interactor.never_ask

import com.g2pdev.smartrate.data.repository.RateRepository

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
