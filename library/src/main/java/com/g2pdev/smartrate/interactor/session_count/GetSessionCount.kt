package com.g2pdev.smartrate.interactor.session_count

import com.g2pdev.smartrate.repository.RateRepository

internal interface GetSessionCount {
    fun exec(): Int
}

internal class GetSessionCountImpl(
    private val rateRepository: RateRepository
) : GetSessionCount {

    override fun exec(): Int {
        return rateRepository.getSessionCount()
    }
}
