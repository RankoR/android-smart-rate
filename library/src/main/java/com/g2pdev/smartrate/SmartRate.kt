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

    /**
     * Initialize library. Call only once in [Application.onCreate].
     * @param context Application context. Other contexts will lead to crash
     */
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

    /**
     * Clear all cached data:
     * - Session count
     * - isRated flag
     * - isNeverAsk flag
     * - Last prompt session
     */
    fun clearAll(callback: (() -> Unit)? = null) {
        rateDisplayer.clearAll(callback)
    }

    /**
     * Smart show rate dialog.
     * Dialog will be shown only if all specified in config conditions match
     *
     * @param activity Activity to show dialog with
     * @param config Config, optional. If not specified, will be used default one
     */
    fun show(activity: FragmentActivity, config: SmartRateConfig = SmartRateConfig()) {
        rateDisplayer.show(activity, config)
    }

    /**
     * WARNING: This method is ONLY for testing!
     */
    @Deprecated("For tests only; usage in other apps will lead to crash!")
    fun testIncrementSessionCount() {
        rateDisplayer.incrementSessionCount(test = true)
    }

}