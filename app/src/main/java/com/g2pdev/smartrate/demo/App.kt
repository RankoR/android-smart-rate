package com.g2pdev.smartrate.demo

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.g2pdev.smartrate.SmartRate
import com.g2pdev.smartrate.demo.di.DiHolder
import com.g2pdev.smartrate.demo.interactor.dark_mode.GetDarkMode
import com.g2pdev.smartrate.demo.interactor.dark_mode.SetDarkMode
import com.g2pdev.smartrate.demo.interactor.fake_session_count.IncrementFakeSessionCount
import com.g2pdev.smartrate.demo.util.LocaleHelper.onAttach
import com.g2pdev.smartrate.demo.util.schedulersSingleToMain
import timber.log.Timber
import javax.inject.Inject


class App : Application() {

    @Inject
    internal lateinit var incrementFakeSessionCount: IncrementFakeSessionCount

    @Inject
    internal lateinit var getDarkMode: GetDarkMode

    @Inject
    internal lateinit var setDarkMode: SetDarkMode

    override fun onCreate() {
        super.onCreate()

        initLogging()
        initDagger()
        initSmartRate()
        incrementFakeSessionCount()
    }

    private fun initDagger() {
        DiHolder.init(this)
        DiHolder.plusAppComponent().inject(this)
        val darkMode = getDarkMode.exec().blockingGet()
        AppCompatDelegate.setDefaultNightMode(if (darkMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)
    }

    private fun initLogging() {
        Timber.plant(Timber.DebugTree())
    }

    private fun initSmartRate() {
        SmartRate.init(this)
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(onAttach(base, "en"))
    }

    @SuppressLint("CheckResult")
    private fun incrementFakeSessionCount() {
        incrementFakeSessionCount
            .exec()
            .schedulersSingleToMain()
            .subscribe({}, Timber::e)
    }
}
