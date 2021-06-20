package com.g2pdev.smartrate.demo.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.lifecycleScope
import com.g2pdev.smartrate.SmartRate
import com.g2pdev.smartrate.demo.R
import com.g2pdev.smartrate.demo.databinding.ActivityMainBinding
import com.g2pdev.smartrate.extension.textChanges
import com.g2pdev.smartrate.logic.model.config.SmartRateConfig
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import timber.log.Timber


@FlowPreview
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    private var smartRateConfig = SmartRateConfig()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding
            .inflate(layoutInflater)
            .also {
                setContentView(it.root)
            }

        setupViewModel()
        setupListeners()
    }

    private fun setupListeners() {
        binding
            .sessionCountEt
            .textChanges(skipInitial = true)
            .debounce(INPUT_DEBOUNCE)
            .map { it.toString() }
            .filter { it.isDigitsOnly() }
            .map { it.toInt() }
            .onEach(viewModel::setSessionCount)
            .launchIn(lifecycleScope)

        binding
            .sessionCountBetweenPromptsEt
            .textChanges(skipInitial = true)
            .debounce(INPUT_DEBOUNCE)
            .map { it.toString() }
            .filter { it.isDigitsOnly() }
            .map { it.toInt() }
            .onEach(viewModel::setSessionCountBetweenPrompts)
            .launchIn(lifecycleScope)

        binding.incrementFakeSessionCountBtn.setOnClickListener {
            viewModel.incrementFakeSessionCount()
        }

        binding.showRateDialogBtn.setOnClickListener {
            SmartRate.show(this, smartRateConfig)
        }

        binding.resetLibraryCountersBtn.setOnClickListener {
            viewModel.clearLibraryCounters()
        }
    }

    private fun setupViewModel() {
        viewModel.config.observe(this) {
            this.smartRateConfig = it

            Timber.d("Set new config: $it")
        }

        viewModel.sessionCount.observe(this) {
            binding.sessionCountEt.setText(it.toString())
        }

        viewModel.sessionCountBetweenPrompts.observe(this) {
            binding.sessionCountBetweenPromptsEt.setText(it.toString())
        }

        viewModel.fakeSessionCount.observe(this) {
            binding.fakeSessionCountTv.text = getString(R.string.format_fake_session_count, it)
        }

        viewModel.showCountersCleared.observe(this) {
            showLogMessage(getString(R.string.title_library_counters_cleared))
        }

        viewModel.showRateDialogShown.observe(this) {
            showLogMessage(getString(R.string.title_rate_dialog_shown))
        }

        viewModel.showRateDialogWillNotShow.observe(this) {
            showLogMessage(getString(R.string.title_rate_dialog_will_not_show))
        }

        viewModel.rating.observe(this) {
            showLogMessage(getString(R.string.format_rated, it))
        }

        viewModel.showNeverAskClicked.observe(this) {
            showLogMessage(getString(R.string.title_never_clicked))
        }

        viewModel.showLaterClicked.observe(this) {
            showLogMessage(getString(R.string.title_later_clicked))
        }

        viewModel.showFeedbackCancelClicked.observe(this) {
            showLogMessage(getString(R.string.title_feedback_cancel_clicked))
        }

        viewModel.showFeedbackSubmitClicked.observe(this) {
            showLogMessage(getString(R.string.format_feedback_submit_clicked, it))
        }
    }

    @SuppressLint("SetTextI18n")
    private fun addLogEntry(text: String) {
        binding.logsTv.text = binding.logsTv.text.toString() + "\n" + text
    }

    private fun showLogMessage(text: String) {
        addLogEntry(text)
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    private companion object {
        private const val INPUT_DEBOUNCE = 200L
    }
}
