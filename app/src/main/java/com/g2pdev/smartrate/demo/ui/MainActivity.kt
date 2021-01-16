package com.g2pdev.smartrate.demo.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.BaseContextWrappingDelegate
import androidx.appcompat.app.OnAttachBaseContext
import androidx.core.text.isDigitsOnly
import com.g2pdev.smartrate.SmartRate
import com.g2pdev.smartrate.demo.R
import com.g2pdev.smartrate.demo.util.LocaleHelper.onAttach
import com.g2pdev.smartrate.demo.util.LocaleHelper.onAttachConfiguration
import com.g2pdev.smartrate.logic.model.config.SmartRateConfig
import com.jakewharton.rxbinding3.widget.afterTextChangeEvents
import com.jakewharton.rxbinding3.widget.checkedChanges
import com.jakewharton.rxbinding3.widget.itemSelections
import com.jakewharton.rxbinding3.widget.textChanges
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import timber.log.Timber
import java.util.concurrent.TimeUnit


class MainActivity : MvpAppCompatActivity(), MainView, OnAttachBaseContext {

    @InjectPresenter
    internal lateinit var presenter: MainPresenter

    private val compositeDisposable = CompositeDisposable()

    private var smartRateConfig = SmartRateConfig()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        setupListeners()
    }

    override fun onDestroy() {
        compositeDisposable.clear()

        super.onDestroy()
    }

    private fun setupListeners() {
        sessionCountEt
            .textChanges()
            .skipInitialValue()
            .map { it.toString() }
            .filter { it.isDigitsOnly() && it.isNotBlank() }
            .skip(1)
            .debounce(inputDebounce, TimeUnit.MILLISECONDS)
            .map {
                try {it.toInt()} catch (ex: Throwable) { 3 }
            }
            .subscribe(presenter::setSessionCount, Timber::e)
            .disposeOnDestroy()

        sessionCountBetweenPromptsEt
            .textChanges()
            .map { it.toString() }
            .filter { it.isDigitsOnly() && it.isNotBlank() }
            .skip(1)
            .debounce(inputDebounce, TimeUnit.MILLISECONDS)
            .map {
                try {it.toInt()} catch (ex: Throwable) { 3 }
            }
            .subscribe(presenter::setSessionCountBetweenPrompts, Timber::e)
            .disposeOnDestroy()

        incrementFakeSessionCountBtn.setOnClickListener {
            presenter.incrementFakeSessionCount()
        }

        showRateDialogBtn.setOnClickListener {
            SmartRate.show(this, smartRateConfig)
        }

        resetLibraryCountersBtn.setOnClickListener {
            presenter.clearLibraryCounters()
        }

        val localesArray = resources.getStringArray(R.array.locales_system)
        localeSelect.setSelection(localesArray.indexOf(presenter.getLocale.exec().blockingGet()))
        localeSelect
            .itemSelections()
                // skip default initial & select programmatically
            .skip(2)
                // prevent double calls
            .debounce(inputDebounce, TimeUnit.MILLISECONDS)
            .map { localesArray[it] }
            .subscribe(presenter::setLanguage, Timber::e)
            .disposeOnDestroy()

        darkModeSwitch.isChecked = presenter.getDarkMode()
        darkModeSwitch
            .checkedChanges()
            .subscribe(presenter::setDarkMode, Timber::e)
            .disposeOnDestroy()
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(onAttach(base))
    }

    override fun createConfigurationContext(overrideConfiguration: Configuration): Context {
        return onAttachConfiguration(overrideConfiguration, this)
    }
    override fun onAttachBaseContext(context: Context): Context {
        return onAttach(context)
    }

    private var baseContextWrappingDelegate: AppCompatDelegate? = null

    override fun getDelegate() = baseContextWrappingDelegate ?: BaseContextWrappingDelegate(
        super.getDelegate(),
        this
    ).apply {
        baseContextWrappingDelegate = this
    }

    override fun showSessionCount(sessionCount: Int) {
        sessionCountEt.setText(sessionCount.toString())
    }

    override fun showSessionCountBetweenPrompts(sessionCountBetweenPrompts: Int) {
        sessionCountBetweenPromptsEt.setText(sessionCountBetweenPrompts.toString())
    }

    override fun setFakeSessionCount(sessionCount: Int) {
        fakeSessionCountTv.text = getString(R.string.format_fake_session_count, sessionCount)
    }

    // workaround for many toasts one after another on restore state
    // uncomment to not save state but show toasts
    /*@SuppressLint("MissingSuperCall")
    override fun onSaveInstanceState(outState: Bundle) {
    }*/

    @SuppressLint("SetTextI18n")
    private fun addLogEntry(text: String) {
        logsTv.text = logsTv.text.toString() + "\n" + text
    }

    private fun showLogMessage(text: String) {
        addLogEntry(text)
        // workaround for many toasts one after another on restore state
        // uncomment to not save state but show toasts
        //Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    override fun showCountersCleared() {
        showLogMessage(getString(R.string.title_library_counters_cleared))
    }

    override fun showRateDialogShown() {
        showLogMessage(getString(R.string.title_rate_dialog_shown))
    }

    override fun showRateDialogWillNotShow() {
        showLogMessage(getString(R.string.title_rate_dialog_will_not_show))
    }

    override fun showRated(stars: Float) {
        showLogMessage(getString(R.string.format_rated, stars))
    }

    override fun showNeverClicked() {
        showLogMessage(getString(R.string.title_never_clicked))
    }

    override fun showLaterClicked() {
        showLogMessage(getString(R.string.title_later_clicked))
    }

    override fun showFeedbackCancelClicked() {
        showLogMessage(getString(R.string.title_feedback_cancel_clicked))
    }

    override fun showFeedbackSubmitClicked(text: String) {
        showLogMessage(getString(R.string.format_feedback_submit_clicked, text))
    }

    override fun setConfig(config: SmartRateConfig) {
        this.smartRateConfig = config

        Timber.d("Set new config: $config")
    }

    private fun Disposable.disposeOnDestroy(): Disposable {
        compositeDisposable.add(this)

        return this
    }

    private companion object {
        private const val inputDebounce = 200L
    }
}
