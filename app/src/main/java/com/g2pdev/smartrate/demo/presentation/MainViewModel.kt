package com.g2pdev.smartrate.demo.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.g2pdev.smartrate.SmartRate
import com.g2pdev.smartrate.data.model.Store
import com.g2pdev.smartrate.data.model.config.SmartRateConfig
import com.g2pdev.smartrate.demo.di.DiHolder
import com.g2pdev.smartrate.demo.domain.interactor.GetSessionCount
import com.g2pdev.smartrate.demo.domain.interactor.SetSessionCount
import com.g2pdev.smartrate.demo.domain.interactor.fake_session_count.ClearFakeSessionCount
import com.g2pdev.smartrate.demo.domain.interactor.fake_session_count.GetFakeSessionCount
import com.g2pdev.smartrate.demo.domain.interactor.fake_session_count.IncrementFakeSessionCount
import com.g2pdev.smartrate.demo.domain.interactor.session_count.GetSessionCountBetweenPrompts
import com.g2pdev.smartrate.demo.domain.interactor.session_count.SetSessionCountBetweenPrompts
import com.g2pdev.smartrate.presentation.base.BaseViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

class MainViewModel : BaseViewModel() {

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

    private val smartRateConfig = SmartRateConfig().apply {
        store = Store.GOOGLE_PLAY_IN_APP_REVIEW
    }

    val config = MutableLiveData<SmartRateConfig>()

    val showRateDialogShown = MutableLiveData<Unit>()
    val showRateDialogWillNotShow = MutableLiveData<Unit>()

    val rating = MutableLiveData<Float>()

    val showCountersCleared = MutableLiveData<Unit>()

    val showNeverAskClicked = MutableLiveData<Unit>()
    val showLaterClicked = MutableLiveData<Unit>()

    val showFeedbackCancelClicked = MutableLiveData<Unit>()
    val showFeedbackSubmitClicked = MutableLiveData<Unit>()

    val fakeSessionCount = MutableLiveData<Int>()
    val sessionCount = MutableLiveData<Int>()

    val sessionCountBetweenPrompts = MutableLiveData<Int>()

    init {
        DiHolder.plusAppComponent().inject(this)

        initConfigListeners()

        updateSessionCount()
        updateSessionCountBetweenPrompts()
        updateFakeSessionCount()
    }

    private fun initConfigListeners() {
        smartRateConfig.apply {
            onRateDialogShowListener = {
                showRateDialogShown.value = Unit
            }

            onRateDialogWillNotShowListener = {
                showRateDialogWillNotShow.value = Unit
            }

            onRateListener = { value ->
                rating.value = value
            }

            onNeverAskClickListener = {
                showNeverAskClicked.value = Unit
            }

            onLaterClickListener = {
                showLaterClicked.value = Unit
            }

            onFeedbackCancelClickListener = {
                showFeedbackCancelClicked.value = Unit
            }
            onFeedbackSubmitClickListener = {
                showFeedbackSubmitClicked.value = Unit
            }
        }
    }

    fun setSessionCount(sessionCount: Int) {
        viewModelScope.launch {
            setSessionCount.exec(sessionCount)
        }
    }

    fun setSessionCountBetweenPrompts(sessionCount: Int) {
        viewModelScope.launch {
            setSessionCountBetweenPrompts.exec(sessionCount)

            smartRateConfig
                .apply { minSessionCountBetweenPrompts = sessionCount }
                .let(config::postValue)
        }
    }

    private fun updateFakeSessionCount() {
        viewModelScope.launch {
            getFakeSessionCount
                .exec()
                .let(fakeSessionCount::setValue)
        }
    }

    fun incrementFakeSessionCount() {
        viewModelScope.launch {
            incrementFakeSessionCount.exec()

            updateFakeSessionCount()
        }
    }

    private fun updateSessionCount() {
        viewModelScope.launch {
            getSessionCount
                .exec()
                .let(sessionCount::postValue)
        }
    }

    private fun updateSessionCountBetweenPrompts() {
        viewModelScope.launch {
            getSessionCountBetweenPrompts
                .exec()
                .let(sessionCountBetweenPrompts::postValue)
        }
    }

    fun clearLibraryCounters() {
        viewModelScope.launch {
            clearFakeSessionCount.exec()
            updateFakeSessionCount()
            SmartRate.clearAll {
                showCountersCleared.postValue(Unit)
            }
        }
    }
}
