package com.g2pdev.smartrate.interactor

import com.g2pdev.smartrate.repository.RateRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface ClearAll {
    suspend fun exec()
}

internal class ClearAllImpl(
    private val rateRepository: RateRepository
) : ClearAll {

    override suspend fun exec() {
        withContext(Dispatchers.IO) {
            rateRepository.clearAll()
        }
    }
}
