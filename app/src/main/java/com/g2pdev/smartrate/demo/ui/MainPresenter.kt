package com.g2pdev.smartrate.demo.ui

import android.content.Context
import com.g2pdev.smartrate.SmartRate
import com.g2pdev.smartrate.demo.di.DiHolder
import com.g2pdev.smartrate.demo.interactor.GetSessionCount
import com.g2pdev.smartrate.demo.interactor.SetSessionCount
import com.g2pdev.smartrate.demo.interactor.dark_mode.GetDarkMode
import com.g2pdev.smartrate.demo.interactor.dark_mode.SetDarkMode
import com.g2pdev.smartrate.demo.interactor.fake_session_count.ClearFakeSessionCount
import com.g2pdev.smartrate.demo.interactor.fake_session_count.GetFakeSessionCount
import com.g2pdev.smartrate.demo.interactor.fake_session_count.IncrementFakeSessionCount
import com.g2pdev.smartrate.demo.interactor.language.GetLocale
import com.g2pdev.smartrate.demo.interactor.language.SetLocale
import com.g2pdev.smartrate.demo.interactor.session_count.GetSessionCountBetweenPrompts
import com.g2pdev.smartrate.demo.interactor.session_count.SetSessionCountBetweenPrompts
import com.g2pdev.smartrate.demo.util.schedulersSingleToMain
import com.g2pdev.smartrate.logic.model.Store
import com.g2pdev.smartrate.logic.model.config.SmartRateConfig
import javax.inject.Inject
import moxy.InjectViewState
import timber.log.Timber

@InjectViewState
class MainPresenter : BasePresenter<MainView>() {

    private var mainActivity: MainActivity? = null

    override fun onDestroy() {
        mainActivity = null
        super.onDestroy()
    }

    @Inject
    internal lateinit var getSessionCount: GetSessionCount

    @Inject
    internal lateinit var setSessionCount: SetSessionCount

    @Inject
    internal lateinit var getSessionCountBetweenPrompts: GetSessionCountBetweenPrompts

    @Inject
    internal lateinit var setSessionCountBetweenPrompts: SetSessionCountBetweenPrompts

    @Inject
    internal lateinit var getFakeSessionCount: GetFakeSessionCount

    @Inject
    internal lateinit var incrementFakeSessionCount: IncrementFakeSessionCount

    @Inject
    internal lateinit var clearFakeSessionCount: ClearFakeSessionCount

    @Inject
    internal lateinit var getLocale: GetLocale

    @Inject
    internal lateinit var setLocale: SetLocale

    @Inject
    internal lateinit var getDarkMode: GetDarkMode

    @Inject
    internal lateinit var setDarkMode: SetDarkMode

    private val smartRateConfig =
        SmartRateConfig()

    init {
        DiHolder.plusAppComponent().inject(this)
    }

    override fun attachView(view: MainView?) {
        super.attachView(view)

        // should always be MainActivity as it's the delegate in our case
        if (view is MainActivity) {
            mainActivity = view
        }

        initConfigListeners()

        updateSessionCount()
        updateSessionCountBetweenPrompts()
        updateFakeSessionCount()
    }

    override fun detachView(view: MainView?) {
        super.detachView(view)
        mainActivity = null
    }

    private fun initConfigListeners() {
        smartRateConfig.apply {
            onRateDialogShowListener = viewState::showRateDialogShown
            onRateDialogWillNotShowListener = viewState::showRateDialogWillNotShow

            onRateListener = viewState::showRated

            onNeverAskClickListener = viewState::showNeverClicked
            onLaterClickListener = viewState::showLaterClicked

            onFeedbackCancelClickListener = viewState::showFeedbackCancelClicked
            onFeedbackSubmitClickListener = viewState::showFeedbackSubmitClicked
        }
    }

    private fun updateSessionCount() {
        getSessionCount
            .exec()
            .schedulersSingleToMain()
            .subscribe(viewState::showSessionCount, Timber::e)
            .disposeOnDestroy()
    }

    private fun updateSessionCountBetweenPrompts() {
        getSessionCountBetweenPrompts
            .exec()
            .schedulersSingleToMain()
            .subscribe(viewState::showSessionCountBetweenPrompts, Timber::e)
            .disposeOnDestroy()
    }

    fun setSessionCount(sessionCount: Int) {
        setSessionCount
            .exec(sessionCount)
            .schedulersSingleToMain()
            .subscribe({
                viewState.setConfig(
                    smartRateConfig
                        .apply { minSessionCount = sessionCount }
                )
            }, Timber::e)
            .disposeOnDestroy()
    }

    fun setSessionCountBetweenPrompts(sessionCount: Int) {
        setSessionCountBetweenPrompts
            .exec(sessionCount)
            .schedulersSingleToMain()
            .subscribe({
                viewState.setConfig(
                    smartRateConfig
                        .apply { minSessionCountBetweenPrompts = sessionCount }
                )
            }, Timber::e)
            .disposeOnDestroy()
    }

    fun setLanguage(language: String) {
        setLocale
            .exec(language)
            .schedulersSingleToMain()
            .subscribe({
                mainActivity?.recreate()
            }, Timber::e)
            .disposeOnDestroy()
    }

    fun setDarkMode(enabled: Boolean) {
        setDarkMode
            .exec(enabled)
            .schedulersSingleToMain()
            .subscribe({
                //mainActivity.recreate()
            }, Timber::e)
            .disposeOnDestroy()
    }

    fun getDarkMode(): Boolean {
        return getDarkMode.exec().blockingGet()
    }

    private fun updateFakeSessionCount() {
        getFakeSessionCount
            .exec()
            .schedulersSingleToMain()
            .subscribe(viewState::setFakeSessionCount, Timber::e)
            .disposeOnDestroy()
    }

    fun incrementFakeSessionCount() {
        incrementFakeSessionCount
            .exec()
            .schedulersSingleToMain()
            .subscribe(::updateFakeSessionCount, Timber::e)
            .disposeOnDestroy()
    }

    fun clearLibraryCounters() {
        clearFakeSessionCount
            .exec()
            .schedulersSingleToMain()
            .subscribe({
                updateFakeSessionCount()
                SmartRate.instance?.clearAll(viewState::showCountersCleared)
            }, Timber::e)
            .disposeOnDestroy()
    }
}
