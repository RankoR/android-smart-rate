package com.g2pdev.smartrate.interactor.session_count

import com.g2pdev.smartrate.repository.RateRepository
import io.reactivex.Single

internal interface GetSessionCount {
    fun exec(): Single<Int>
}

internal class GetSessionCountImpl(
    private val rateRepository: RateRepository
) : GetSessionCount {

    override fun exec(): Single<Int> {
        return rateRepository.getSessionCount()
    }
}
