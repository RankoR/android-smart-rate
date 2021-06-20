package com.g2pdev.smartrate.demo.domain.interactor.session_count

import com.g2pdev.smartrate.demo.data.repository.SettingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Sets count of sessions between prompts (when user clicks «maybe later»)
 */
interface SetSessionCountBetweenPrompts {
    suspend fun exec(sessionCount: Int)
}

internal class SetSessionCountBetweenPromptsImpl(
    private val settingsRepository: SettingsRepository
) : SetSessionCountBetweenPrompts {

    override suspend fun exec(sessionCount: Int) {
        withContext(Dispatchers.IO) {
            settingsRepository.setSessionCountBetweenPrompts(sessionCount)
        }
    }
}
