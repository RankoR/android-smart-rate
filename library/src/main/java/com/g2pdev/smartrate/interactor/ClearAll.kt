package com.g2pdev.smartrate.interactor

import com.g2pdev.smartrate.repository.RateRepository
import io.reactivex.Completable

interface ClearAll {
    fun exec(): Completable
}

internal class ClearAllImpl(
    private val rateRepository: RateRepository
) : ClearAll {

    override fun exec(): Completable {
        return rateRepository.clearAll()
    }
}