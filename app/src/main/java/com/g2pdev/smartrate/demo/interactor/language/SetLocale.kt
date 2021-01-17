package com.g2pdev.smartrate.demo.interactor.language

import com.g2pdev.smartrate.demo.repository.SettingsRepository
import io.reactivex.Completable

interface SetLocale {
    fun exec(locale: String): Completable
}

internal class SetLocaleImpl(
    private val settingsRepository: SettingsRepository
) : SetLocale {

    override fun exec(locale: String): Completable {
        return settingsRepository.setLocale(locale)
    }
}
