package com.g2pdev.smartrate

import android.app.Application
import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.g2pdev.smartrate.di.DaggerRateComponent
import com.g2pdev.smartrate.di.RateComponent
import com.g2pdev.smartrate.di.RateModule
import com.g2pdev.smartrate.logic.RateDisplayer
import com.g2pdev.smartrate.logic.model.SmartRateConfig
import timber.log.Timber


object SmartRate {

    private lateinit var rateComponent: RateComponent

    private lateinit var rateDisplayer: RateDisplayer

    fun init(context: Context) {
        if (context !is Application) {
            throw IllegalArgumentException("Context must be application context")
        }

        initDagger(context)
        initLogging()
        initRateDisplayer()
    }

    private fun initDagger(context: Context) {
        if (::rateComponent.isInitialized) {
            throw IllegalStateException("Already initialized")
        }

        rateComponent = DaggerRateComponent
            .builder()
            .rateModule(RateModule(context))
            .build()
    }

    private fun initLogging() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun initRateDisplayer() {
        rateDisplayer = RateDisplayer()
    }

    internal fun plusRateComponent(): RateComponent {
        return rateComponent
    }

    fun clearAll(callback: (() -> Unit)? = null) {
        rateDisplayer.clearAll(callback)
    }

    fun show(activity: FragmentActivity, config: SmartRateConfig = SmartRateConfig()) {
        rateDisplayer.show(activity, config)
    }

    /**
     * WARNING: This method is ONLY for testing!
     */
    fun testIncrementSessionCount() {
        rateDisplayer.incrementSessionCount(test = true)
    }

}