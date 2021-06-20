package com.g2pdev.smartrate.demo.interactor

import com.g2pdev.smartrate.demo.repository.SettingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface GetSessionCount {
    suspend fun exec(): Int
}

internal class GetSessionCountImpl(
    private val settingsRepository: SettingsRepository
) : GetSessionCount {

    override suspend fun exec(): Int {
        return withContext(Dispatchers.IO) {
            settingsRepository.getSessionCount()
        }
    }
}
