package com.g2pdev.smartrate.interactor.is_rated

import com.g2pdev.smartrate.repository.RateRepository
import io.reactivex.Single

internal interface IsRated {
    fun exec(): Single<Boolean>
}

internal class IsRatedImpl(
    private val rateRepository: RateRepository
) : IsRated {

    override fun exec(): Single<Boolean> {
        return rateRepository.isRated()
    }
}