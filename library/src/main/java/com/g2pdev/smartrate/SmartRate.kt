package com.g2pdev.smartrate

import android.app.Application
import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.g2pdev.smartrate.data.model.config.SmartRateConfig
import com.g2pdev.smartrate.di.DaggerRateComponent
import com.g2pdev.smartrate.di.RateComponent
import com.g2pdev.smartrate.di.RateModule
import com.g2pdev.smartrate.domain.RateDisplayer
import timber.log.Timber

class SmartRate {

    companion object {
        @JvmStatic
        public var instance: SmartRate? = null
        @JvmStatic
        fun init(context: Application) {
            instance = SmartRate()
            instance!!.initReal(context)
        }
        @JvmStatic
        /**
         * Smart show rate dialog.
         * Dialog will be shown only if all specified in config conditions match
         *
         * @param activity Activity to show dialog with
         * @param config Config, optional. If not specified, will be used default one
         */
        fun show(activity: FragmentActivity, config: SmartRateConfig = SmartRateConfig()) {
            instance?.rateDisplayer?.show(activity, config)
        }
    }

    private lateinit var rateComponent: RateComponent

    private lateinit var rateDisplayer: RateDisplayer

    /**
     * Initialize library. Call only once in [Application.onCreate].
     * @param context Application context. Other contexts will lead to crash
     */
    private fun initReal(context: Application) {
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
     * WARNING: This method is ONLY for testing!
     */
    @Deprecated("For tests only; usage in other apps will lead to crash!")
    fun testIncrementSessionCount() {
        rateDisplayer.incrementSessionCount(test = true)
    }
}
