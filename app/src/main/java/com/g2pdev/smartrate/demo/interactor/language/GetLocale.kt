package com.g2pdev.smartrate.demo.interactor.language

import com.g2pdev.smartrate.demo.repository.SettingsRepository
import io.reactivex.Single

interface GetLocale {
    fun exec(): Single<String>
}

internal class GetLocaleImpl(
    private val settingsRepository: SettingsRepository
) : GetLocale {

    override fun exec(): Single<String> {
        return settingsRepository.getLocale()
    }
}
