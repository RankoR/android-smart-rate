package com.g2pdev.smartrate.demo.ui

import android.os.Bundle
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import com.g2pdev.smartrate.SmartRate
import com.g2pdev.smartrate.demo.R
import com.g2pdev.smartrate.logic.model.config.SmartRateConfig
import com.jakewharton.rxbinding3.widget.textChanges
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import timber.log.Timber
import java.util.concurrent.TimeUnit

class MainActivity : MvpAppCompatActivity(), MainView {

    @InjectPresenter
    internal lateinit var presenter: MainPresenter

    private val compositeDisposable = CompositeDisposable()

    private var smartRateConfig =
        SmartRateConfig()

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
            .debounce(inputDebounce, TimeUnit.MILLISECONDS)
            .map { it.toString() }
            .filter { it.isDigitsOnly() }
            .map { it.toInt() }
            .subscribe(presenter::setSessionCount, Timber::e)
            .disposeOnDestroy()

        sessionCountBetweenPromptsEt
            .textChanges()
            .skipInitialValue()
            .debounce(inputDebounce, TimeUnit.MILLISECONDS)
            .map { it.toString() }
            .filter { it.isDigitsOnly() }
            .map { it.toInt() }
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

    override fun showCountersCleared() {
        Toast.makeText(
            this@MainActivity,
            R.string.title_library_counters_cleared,
            Toast.LENGTH_LONG
        ).show()
    }

    override fun showRateDialogShown() {
        Toast.makeText(this, R.string.title_rate_dialog_shown, Toast.LENGTH_LONG).show()
    }

    override fun showRateDialogWillNotShow() {
        Toast.makeText(this, R.string.title_rate_dialog_will_not_show, Toast.LENGTH_LONG).show()
    }

    override fun showRated(stars: Float) {
        Toast.makeText(this, getString(R.string.format_rated, stars), Toast.LENGTH_LONG).show()
    }

    override fun showNeverClicked() {
        Toast.makeText(this, R.string.title_never_clicked, Toast.LENGTH_LONG).show()
    }

    override fun showLaterClicked() {
        Toast.makeText(this, R.string.title_later_clicked, Toast.LENGTH_LONG).show()
    }

    override fun showFeedbackCancelClicked() {
        Toast.makeText(this, R.string.title_feedback_cancel_clicked, Toast.LENGTH_LONG).show()
    }

    override fun showFeedbackSubmitClicked(text: String) {
        Toast.makeText(
            this,
            getString(R.string.format_feedback_submit_clicked, text),
            Toast.LENGTH_LONG
        ).show()
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
