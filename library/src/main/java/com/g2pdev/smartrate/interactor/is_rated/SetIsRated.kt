package com.g2pdev.smartrate.interactor.is_rated

import com.g2pdev.smartrate.repository.RateRepository
import io.reactivex.Completable

internal interface SetIsRated {
    fun exec(isRated: Boolean): Completable
}

internal class SetIsRatedImpl(
    private val rateRepository: RateRepository
) : SetIsRated {

    override fun exec(isRated: Boolean): Completable {
        return rateRepository.setIsRated(isRated)
    }
}