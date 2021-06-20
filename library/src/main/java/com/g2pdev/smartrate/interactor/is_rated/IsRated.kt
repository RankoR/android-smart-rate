package com.g2pdev.smartrate.interactor.is_rated

import com.g2pdev.smartrate.repository.RateRepository

internal interface IsRated {
    fun exec(): Boolean
}

internal class IsRatedImpl(
    private val rateRepository: RateRepository
) : IsRated {

    override fun exec(): Boolean {
        return rateRepository.isRated()
    }
}
