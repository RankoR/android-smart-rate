package com.g2pdev.smartrate.interactor.is_rated

import com.g2pdev.smartrate.repository.RateRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal interface SetIsRated {
    suspend fun exec(isRated: Boolean)
}

internal class SetIsRatedImpl(
    private val rateRepository: RateRepository
) : SetIsRated {

    override suspend fun exec(isRated: Boolean) {
        withContext(Dispatchers.IO) {
            rateRepository.setIsRated(isRated)
        }
    }
}
