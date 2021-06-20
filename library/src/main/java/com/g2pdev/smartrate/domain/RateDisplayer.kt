package com.g2pdev.smartrate.domain

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.fragment.app.FragmentActivity
import com.g2pdev.smartrate.SmartRate
import com.g2pdev.smartrate.data.model.Store
import com.g2pdev.smartrate.data.model.StoreLink
import com.g2pdev.smartrate.data.model.config.SmartRateConfig
import com.g2pdev.smartrate.data.model.config.getFeedbackConfig
import com.g2pdev.smartrate.data.model.config.getRateConfig
import com.g2pdev.smartrate.domain.interactor.ClearAll
import com.g2pdev.smartrate.domain.interactor.GetPackageName
import com.g2pdev.smartrate.domain.interactor.ShouldShowRating
import com.g2pdev.smartrate.domain.interactor.is_rated.SetIsRated
import com.g2pdev.smartrate.domain.interactor.last_prompt.SetLastPromptSessionToCurrent
import com.g2pdev.smartrate.domain.interactor.never_ask.SetNeverAsk
import com.g2pdev.smartrate.domain.interactor.session_count.IncrementSessionCount
import com.g2pdev.smartrate.domain.interactor.store.GetStoreLink
import com.g2pdev.smartrate.presentation.feedback.FeedbackDialogFragment
import com.g2pdev.smartrate.presentation.rate.RateDialogFragment
import com.google.android.play.core.review.ReviewManagerFactory
import java.lang.ref.WeakReference
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

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

    private val scope = MainScope()

    init {
        if (SmartRate.instance == null) {
            throw IllegalStateException("Not initialized")
        }
        SmartRate.instance!!.plusRateComponent().inject(this)

        incrementSessionCount()
    }

    @SuppressLint("CheckResult")
    fun incrementSessionCount(test: Boolean = false) {
        if (test) {
            // Crash if package name is not Demo
            if (getPackageName.exec() != DEMO_APP_PACKAGE_NAME) {
                throw IllegalAccessError("Do not call test methods in real apps!")
            }
        }

        scope.launch {
            incrementSessionCount.exec()
        }
    }

    fun clearAll(callback: (() -> Unit)? = null) {
        scope.launch {
            clearAll.exec()

            withContext(Dispatchers.Main) {
                callback?.invoke()
            }
        }
    }

    fun show(activity: FragmentActivity, config: SmartRateConfig) {
        scope.launch {
            val shouldShow = shouldShowRating.exec(config.minSessionCount, config.minSessionCountBetweenPrompts)

            withContext(Dispatchers.Main) {
                if (shouldShow) {
                    showRateDialog(activity, config)
                } else {
                    config.onRateDialogWillNotShowListener?.invoke()
                }
            }
        }
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
                            openStoreLink(activity, activity, config)
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

    private fun setLastPromptSessionToCurrent() {
        scope.launch {
            setLastPromptSessionToCurrent.exec()
        }
    }

    private fun setNeverAsk() {
        scope.launch {
            setNeverAsk.exec(true)
        }
    }

    private fun setIsRated() {
        scope.launch {
            setIsRated.exec(true)
        }
    }

    private fun openStoreLink(context: Context, activity: Activity, config: SmartRateConfig) {
        if (config.store == Store.GOOGLE_PLAY_IN_APP_REVIEW) {
            launchInAppReviewFlow(context, activity)
            return
        }

        try {
            getStoreIntent(context, config).let(context::startActivity)
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    private fun launchInAppReviewFlow(context: Context, activity: Activity) {
        val reviewManager = ReviewManagerFactory.create(context)
        reviewManager.requestReviewFlow().apply {
            addOnCompleteListener { task ->
                val reviewInfo = task.result

                Timber.d("Request complete: $reviewInfo")

                reviewManager.launchReviewFlow(activity, reviewInfo).apply {
                    addOnCompleteListener {
                        Timber.d("Review flow complete")
                    }

                    addOnFailureListener { e ->
                        Timber.e(e, "Failed to launch the review flow")
                    }
                }
            }

            addOnFailureListener {
                Timber.e(it, "Request failed")
            }
        }
    }

    private fun getStoreIntent(context: Context, config: SmartRateConfig): Intent {
        return config
            .customStoreLink
            ?.let { link ->
                createIntentForLink(link)
            }
            ?: getPredefinedStoreIntent(context, config)
    }

    private fun getPredefinedStoreIntent(context: Context, config: SmartRateConfig): Intent {
        return getPackageName
            .exec()
            .let { getStoreLink.exec(config.store, it) }
            .let { getIntentForLink(context, it) }
    }

    private fun getIntentForLink(context: Context, storeLink: StoreLink): Intent {
        return createIntentForLink(storeLink.link)
            .takeIf {
                context.canLaunch(it)
            }
            ?: createIntentForLink(storeLink.alternateLink)
    }

    private fun createIntentForLink(link: String): Intent {
        return Intent(Intent.ACTION_VIEW, Uri.parse(link))
    }

    private fun Context.canLaunch(intent: Intent): Boolean {
        return intent.resolveActivity(packageManager) != null
    }

    private companion object {
        private const val DEMO_APP_PACKAGE_NAME = "com.g2pdev.smartrate.demo"
    }
}
