package com.g2pdev.smartrate.demo.interactor.dark_mode

import com.g2pdev.smartrate.demo.repository.SettingsRepository
import io.reactivex.Single

interface GetDarkMode {
    fun exec(): Single<Boolean>
}

internal class GetDarkModeImpl(
    private val settingsRepository: SettingsRepository
) : GetDarkMode {

    override fun exec(): Single<Boolean> {
        return settingsRepository.getDarkMode()
    }
}
