package com.g2pdev.smartrate.demo.interactor.dark_mode

import com.g2pdev.smartrate.demo.repository.SettingsRepository
import io.reactivex.Completable

interface SetDarkMode {
    fun exec(enable: Boolean): Completable
}

internal class SetDarkModeImpl(
    private val settingsRepository: SettingsRepository
) : SetDarkMode {

    override fun exec(enable: Boolean): Completable {
        return settingsRepository.setDarkMode(enable)
    }
}
