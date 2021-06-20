package com.g2pdev.smartrate.ui.feedback

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.g2pdev.smartrate.databinding.FragmentDialogFeedbackBinding
import com.g2pdev.smartrate.extension.getColorCompat
import com.g2pdev.smartrate.extension.textChanges
import com.g2pdev.smartrate.logic.model.config.FeedbackConfig
import com.g2pdev.smartrate.ui.base.BaseBottomDialogFragment
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

internal class FeedbackDialogFragment : BaseBottomDialogFragment() {

    private var binding: FragmentDialogFeedbackBinding? = null
    private val viewModel: FeedbackDialogViewModel by viewModels()

    var onCancelListener: (() -> Unit)? = null
    var onSubmitListener: ((text: String) -> Unit)? = null

    override fun getFragmentTag() = fragmentTag

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentDialogFeedbackBinding
            .inflate(inflater, container, false)
            .also {
                binding = it
            }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val config = arguments?.getParcelable<FeedbackConfig>(ARG_CONFIG) ?: run {
            close()
            return
        }

        binding?.titleTv?.setText(config.titleResId)
        binding?.feedbackTil?.hint = getString(config.hintResId)

        binding?.cancelBtn?.setText(config.cancelResId)
        binding?.submitBtn?.setText(config.submitResId)

        context?.getColorCompat(config.titleTextColorResId)?.let { resId ->
            binding?.titleTv?.setText(resId)
        }

        context?.getColorCompat(config.cancelButtonTextColorResId)?.let { resId ->
            binding?.cancelBtn?.setText(resId)
        }

        config.submitButtonTextColorResId?.let {
            context?.getColorCompat(it)?.let { color ->
                binding?.submitBtn?.setTextColor(color)
            }
        }

        if (!config.isDismissible) {
            disableDismiss()
        }

        viewModel.minFeedbackLength = config.minFeedbackLength

        setupViewModel()
        setupListeners()
    }

    override fun onDestroyView() {
        binding = null

        super.onDestroyView()
    }

    private fun setupViewModel() {
        viewModel.isSubmitButtonEnabled.observe(viewLifecycleOwner) { isEnabled ->
            binding?.submitBtn?.isEnabled = isEnabled
        }
    }

    private fun setupListeners() {
        binding
            ?.feedbackEt
            ?.textChanges()
            ?.map { it.toString() }
            ?.map { it.trim() }
            ?.onEach(viewModel.feedbackText::setValue)
            ?.launchIn(lifecycleScope)

        binding?.cancelBtn?.setOnClickListener {
            onCancelListener?.invoke()
            close()
        }

        binding?.submitBtn?.setOnClickListener {
            val text = getFeedbackText()

            onSubmitListener?.invoke(text)
            close()
        }
    }

    private fun getFeedbackText(): String = binding?.feedbackEt?.text.toString().trim()

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(ARG_TEXT, getFeedbackText())

        super.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        savedInstanceState?.getString(ARG_TEXT)?.let { text ->
            binding?.feedbackEt?.setText(text)
        }
    }

    fun close() {
        onDismissListener = null
        dismiss()
    }

    companion object {

        private const val ARG_CONFIG = "config"
        private const val ARG_TEXT = "text"

        fun newInstance(config: FeedbackConfig): FeedbackDialogFragment {
            return FeedbackDialogFragment().apply {
                arguments = bundleOf(ARG_CONFIG to config)
            }
        }

        private const val fragmentTag = "FeedbackDialogFragment"
    }
}
