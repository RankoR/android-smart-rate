package com.g2pdev.smartrate.ui.feedback

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.g2pdev.smartrate.R
import com.g2pdev.smartrate.extension.getColorCompat
import com.g2pdev.smartrate.extension.schedulersIoToMain
import com.g2pdev.smartrate.logic.model.config.FeedbackConfig
import com.g2pdev.smartrate.ui.base.BaseBottomDialogFragment
import com.jakewharton.rxbinding3.widget.textChanges
import kotlinx.android.synthetic.main.fragment_dialog_feedback.*
import moxy.presenter.InjectPresenter
import timber.log.Timber

internal class FeedbackDialogFragment : BaseBottomDialogFragment(), FeedbackView {

    @InjectPresenter
    lateinit var presenter: FeedbackPresenter

    var onCancelListener: (() -> Unit)? = null
    var onSubmitListener: ((text: String) -> Unit)? = null

    override fun getFragmentTag() = fragmentTag

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dialog_feedback, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments
            ?.getParcelable<FeedbackConfig>(argConfig)
            ?.let { config ->
                titleTv.setText(config.titleResId)
                feedbackTil.hint = getString(config.hintResId)

                cancelBtn.setText(config.cancelResId)
                submitBtn.setText(config.submitResId)

                context?.getColorCompat(config.titleTextColorResId)?.let(titleTv::setTextColor)
                context?.getColorCompat(config.cancelButtonTextColorResId)
                    ?.let(cancelBtn::setTextColor)
                config.submitButtonTextColorResId?.let {
                    context?.getColorCompat(it)?.let(submitBtn::setTextColor)
                }

                if (!config.isDismissible) {
                    disableDismiss()
                }

                presenter.setMinFeedbackLength(config.minFeedbackLength)
            } ?: run(::close)

        setupListeners()
    }

    private fun setupListeners() {
        feedbackEt
            .textChanges()
            .map { it.toString() }
            .map { it.trim() }
            .schedulersIoToMain()
            .subscribe(presenter::onFeedbackTextChanged, Timber::e)
            .disposeOnDestroy()

        cancelBtn.setOnClickListener {
            onCancelListener?.invoke()
            close()
        }

        submitBtn.setOnClickListener {
            val text = getFeedbackText()

            onSubmitListener?.invoke(text)
            close()
        }
    }

    private fun getFeedbackText(): String = feedbackEt.text.toString().trim()

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(argText, getFeedbackText())

        super.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        savedInstanceState?.getString(argText)?.let(feedbackEt::setText)
    }

    override fun enableSubmitButton(enable: Boolean) {
        submitBtn.isEnabled = enable
    }

    override fun close() {
        onDismissListener = null
        dismiss()
    }

    companion object {

        private const val argConfig = "config"
        private const val argText = "text"

        fun newInstance(config: FeedbackConfig): FeedbackDialogFragment {
            return FeedbackDialogFragment()
                .apply {
                    arguments = bundleOf(argConfig to config)
                }
        }

        private const val fragmentTag = "FeedbackDialogFragment"
    }
}