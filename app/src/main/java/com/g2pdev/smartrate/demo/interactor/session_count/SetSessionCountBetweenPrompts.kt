package com.g2pdev.smartrate.demo.interactor.session_count

import com.g2pdev.smartrate.demo.repository.SettingsRepository
import io.reactivex.Completable

interface SetSessionCountBetweenPrompts {
    fun exec(sessionCount: Int): Completable
}

internal class SetSessionCountBetweenPromptsImpl(
    private val settingsRepository: SettingsRepository
) : SetSessionCountBetweenPrompts {

    override fun exec(sessionCount: Int): Completable {
        return settingsRepository.setSessionCountBetweenPrompts(sessionCount)
    }
}