package com.g2pdev.smartrate.demo.repository

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import com.g2pdev.smartrate.demo.cache.DarkModeCache
import com.g2pdev.smartrate.demo.cache.FakeSessionCountCache
import com.g2pdev.smartrate.demo.cache.SessionCountBetweenPromptsCache
import com.g2pdev.smartrate.demo.cache.SessionCountCache
import com.g2pdev.smartrate.demo.util.LocaleHelper
import io.reactivex.Completable
import io.reactivex.Single
import kotlinx.android.synthetic.main.activity_main.view.*
import java.util.*

/**
 * Stores and retrieves various parameters for app and library
 */
interface SettingsRepository {
    fun getSessionCount(): Single<Int>
    fun setSessionCount(sessionCount: Int): Completable

    fun getSessionCountBetweenPrompts(): Single<Int>
    fun setSessionCountBetweenPrompts(sessionCount: Int): Completable

    fun getFakeSessionCount(): Single<Int>
    fun setFakeSessionCount(sessionCount: Int): Completable

    fun getLocale(): Single<String>
    fun setLocale(locale: String): Completable

    fun getDarkMode(): Single<Boolean>
    fun setDarkMode(enabled: Boolean): Completable
}

internal class SettingsRepositoryImpl(
    private val sessionCountCache: SessionCountCache,
    private val sessionCountBetweenPromptsCache: SessionCountBetweenPromptsCache,
    private val fakeSessionCountCache: FakeSessionCountCache,
    private val darkModeCache: DarkModeCache,
    private val context: Context
) : SettingsRepository {

    override fun getSessionCount(): Single<Int> {
        return sessionCountCache.get(defaultSessionCount)
    }

    override fun setSessionCount(sessionCount: Int): Completable {
        return sessionCountCache.put(sessionCount)
    }

    override fun getSessionCountBetweenPrompts(): Single<Int> {
        return sessionCountBetweenPromptsCache.get(defaultSessionCountBetweenPrompts)
    }

    override fun setSessionCountBetweenPrompts(sessionCount: Int): Completable {
        return sessionCountBetweenPromptsCache.put(sessionCount)
    }

    override fun getFakeSessionCount(): Single<Int> {
        return fakeSessionCountCache.get(defaultFakeSessionCount)
    }

    override fun setFakeSessionCount(sessionCount: Int): Completable {
        return fakeSessionCountCache.put(sessionCount)
    }

    override fun getLocale(): Single<String> {
        val current: Locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context.resources.configuration.locales[0]
        } else {
            context.resources.configuration.locale
        }
        return Single.just(current.language)
    }

    override fun setLocale(localeIn: String): Completable {
        return if (LocaleHelper.setLocaleResult(context, localeIn)) {
            Completable.complete()
        } else {
            Completable.error(Exception("same locale"))
        }
    }

    override fun getDarkMode(): Single<Boolean> {
        return Single.just(
            darkModeCache
                .get(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
                .blockingGet()
        )
    }

    override fun setDarkMode(enabled: Boolean): Completable {
        if (getDarkMode().blockingGet() == enabled) {
            return Completable.error(Exception("same theme"))
        }
        darkModeCache.putSync(enabled)
        AppCompatDelegate.setDefaultNightMode(if (enabled) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)
        return Completable.complete()
    }


    private companion object {
        private const val defaultSessionCount = 3
        private const val defaultSessionCountBetweenPrompts = 3
        private const val defaultFakeSessionCount = 0
    }
}
