package com.g2pdev.smartrate.domain.interactor.session_count

import com.g2pdev.smartrate.data.repository.RateRepository

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
