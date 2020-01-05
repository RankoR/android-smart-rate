package com.g2pdev.smartrate.logic

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.fragment.app.FragmentActivity
import com.g2pdev.smartrate.SmartRate
import com.g2pdev.smartrate.extension.schedulersIoToMain
import com.g2pdev.smartrate.extension.schedulersSingleToMain
import com.g2pdev.smartrate.interactor.ClearAll
import com.g2pdev.smartrate.interactor.GetPackageName
import com.g2pdev.smartrate.interactor.ShouldShowRating
import com.g2pdev.smartrate.interactor.is_rated.SetIsRated
import com.g2pdev.smartrate.interactor.last_prompt.SetLastPromptSessionToCurrent
import com.g2pdev.smartrate.interactor.never_ask.SetNeverAsk
import com.g2pdev.smartrate.interactor.session_count.IncrementSessionCount
import com.g2pdev.smartrate.interactor.store.GetStoreLink
import com.g2pdev.smartrate.logic.model.config.SmartRateConfig
import com.g2pdev.smartrate.logic.model.StoreLink
import com.g2pdev.smartrate.ui.feedback.FeedbackDialogFragment
import com.g2pdev.smartrate.ui.rate.RateDialogFragment
import io.reactivex.Single
import timber.log.Timber
import java.lang.ref.WeakReference
import javax.inject.Inject

internal class RateDisplayer {
    // Ignoring disposables is OK here, because it could be interrupted only if app dies,
    // and in this case we have nothing to do with it

    @Inject
    lateinit var incrementSessionCount: IncrementSessionCount

    @Inject
    lateinit var setLastPromptSessionToCurrent: SetLastPromptSessionToCurrent

    @Inject
    lateinit var shouldShowRating: ShouldShowRating

    @Inject
    lateinit var setIsRated: SetIsRated

    @Inject
    lateinit var setNeverAsk: SetNeverAsk

    @Inject
    lateinit var getStoreLink: GetStoreLink

    @Inject
    lateinit var getPackageName: GetPackageName

    @Inject
    lateinit var clearAll: ClearAll

    init {
        SmartRate.plusRateComponent().inject(this)

        incrementSessionCount()
    }

    @SuppressLint("CheckResult")
    fun incrementSessionCount(test: Boolean = false) {
        if (test) {
            // Crash if package name is not Demo
            getPackageName
                .exec()
                .map { it != demoAppPackageName }
                .schedulersIoToMain()
                .subscribe({ shouldCrash ->
                    if (shouldCrash) {
                        throw IllegalAccessError("Do not call test methods in real apps!")
                    }
                }, Timber::e)
        }

        incrementSessionCount
            .exec()
            .schedulersSingleToMain()
            .subscribe({}, Timber::e)
    }

    @SuppressLint("CheckResult")
    fun clearAll(callback: (() -> Unit)? = null) {
        clearAll
            .exec()
            .schedulersSingleToMain()
            .subscribe({
                callback?.invoke()
            }, Timber::e)
    }

    @SuppressLint("CheckResult")
    fun show(activity: FragmentActivity, config: SmartRateConfig) {
        shouldShowRating
            .exec(config.minSessionCount, config.minSessionCountBetweenPrompts)
            .schedulersSingleToMain()
            .subscribe({ shouldShow ->
                Timber.d("Should show rate dialog: $shouldShow")

                if (shouldShow) {
                    showRateDialog(activity, config)
                }
            }, Timber::e)
    }

    private fun showRateDialog(activity: FragmentActivity, config: SmartRateConfig) {
        val activityWeakReference = WeakReference(activity)

        RateDialogFragment
            .newInstance(config.getRateConfig())
            .apply {
                onRateListener = { rating ->
                    Timber.d("Rated: $rating")

                    config.onRateListener?.invoke(rating)

                    setIsRated()

                    if (rating >= config.minRatingForStore) {
                        activityWeakReference.get()?.let {
                            openStoreLink(activity, config)
                        }
                    } else {
                        if (config.showFeedbackDialog) {
                            showFeedbackDialog(activityWeakReference.get(), config)
                        }
                    }
                }

                onDismissListener = {
                    Timber.d("Dismissed rating")

                    config.onRateDismissListener?.invoke()
                }

                onNeverClickListener = {
                    Timber.d("Never ask clicked")

                    config.onNeverAskClickListener?.invoke()

                    setNeverAsk()
                }

                onLaterClickListener = {
                    Timber.d("Later clicked")

                    config.onLaterClickListener?.invoke()
                }
            }
            .show(activityWeakReference.get())
            .also {
                setLastPromptSessionToCurrent()

                config.onRateDialogShowListener?.invoke()
            }
    }

    private fun showFeedbackDialog(activity: FragmentActivity?, config: SmartRateConfig) {
        FeedbackDialogFragment
            .newInstance(config.getFeedbackConfig())
            .apply {
                onDismissListener = {
                    Timber.d("Feedback dismissed")

                    config.onFeedbackDismissListener?.invoke()
                }

                onCancelListener = {
                    Timber.d("Feedback canceled")

                    config.onFeedbackCancelClickListener?.invoke()
                }

                onSubmitListener = { text ->
                    Timber.d("Feedback submit: $text")

                    config.onFeedbackSubmitClickListener?.invoke(text)
                }
            }
            .show(activity)
    }

    @SuppressLint("CheckResult")
    private fun setLastPromptSessionToCurrent() {
        setLastPromptSessionToCurrent
            .exec()
            .schedulersSingleToMain()
            .subscribe({
                Timber.d("Set last prompt session to current")
            }, Timber::e)
    }

    @SuppressLint("CheckResult")
    private fun setNeverAsk() {
        setNeverAsk
            .exec(true)
            .schedulersSingleToMain()
            .subscribe({
                Timber.d("Set never ask to true")
            }, Timber::e)
    }

    @SuppressLint("CheckResult")
    private fun setIsRated() {
        setIsRated
            .exec(true)
            .schedulersSingleToMain()
            .subscribe({
                Timber.d("Set is rated to true")
            }, Timber::e)
    }

    @SuppressLint("CheckResult")
    private fun openStoreLink(context: Context, config: SmartRateConfig) {
        getStoreIntent(context, config)
            .schedulersSingleToMain()
            .subscribe({ intent ->
                try {
                    context.startActivity(intent)
                } catch (e: Exception) {
                    Timber.e(e)
                }
            }, Timber::e)
    }

    private fun getStoreIntent(context: Context, config: SmartRateConfig): Single<Intent> {
        return config.customStoreLink?.let { link ->
            Single.just(createIntentForLink(link))
        } ?: getPackageName
            .exec()
            .flatMap { getStoreLink.exec(config.store, it) }
            .map { getIntentForLink(context, it) }
    }

    private fun getIntentForLink(context: Context, storeLink: StoreLink): Intent {
        return createIntentForLink(storeLink.link)
            .takeIf {
                context.canLaunch(it)
            } ?: createIntentForLink(storeLink.alternateLink)
    }

    private fun createIntentForLink(link: String): Intent {
        return Intent(Intent.ACTION_VIEW, Uri.parse(link))
    }

    private fun Context.canLaunch(intent: Intent): Boolean {
        return intent.resolveActivity(packageManager) != null
    }

    private companion object {
        private const val demoAppPackageName = "com.g2pdev.smartrate.demo"
    }

}