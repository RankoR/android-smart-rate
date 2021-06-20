package com.g2pdev.smartrate.demo.domain.interactor

import com.g2pdev.smartrate.demo.data.repository.SettingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Set session count for library config
 */
interface SetSessionCount {
    /**
     * Save the session count
     */
    suspend fun exec(sessionCount: Int)
}

internal class SetSessionCountImpl(
    private val settingsRepository: SettingsRepository
) : SetSessionCount {

    override suspend fun exec(sessionCount: Int) {
        withContext(Dispatchers.IO) {
            settingsRepository.setSessionCount(sessionCount)
        }
    }
}
