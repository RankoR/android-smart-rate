package com.g2pdev.smartrate.demo

import android.app.Application
import com.g2pdev.smartrate.SmartRate
import com.g2pdev.smartrate.demo.di.DiHolder
import com.g2pdev.smartrate.demo.domain.interactor.fake_session_count.IncrementFakeSessionCount
import javax.inject.Inject
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import timber.log.Timber

class App : Application() {

    @Inject
    internal lateinit var incrementFakeSessionCount: IncrementFakeSessionCount

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
    }

    private fun initLogging() {
        Timber.plant(Timber.DebugTree())
    }

    private fun initSmartRate() {
        SmartRate.init(this)
    }

    private fun incrementFakeSessionCount() {
        MainScope().launch {
            incrementFakeSessionCount.exec()
        }
    }
}
