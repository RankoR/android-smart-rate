package com.g2pdev.smartrate.demo.interactor

import com.g2pdev.smartrate.demo.repository.SettingsRepository
import io.reactivex.Completable

interface SetSessionCount {
    fun exec(sessionCount: Int): Completable
}

internal class SetSessionCountImpl(
    private val settingsRepository: SettingsRepository
) : SetSessionCount {

    override fun exec(sessionCount: Int): Completable {
        return settingsRepository.setSessionCount(sessionCount)
    }
}