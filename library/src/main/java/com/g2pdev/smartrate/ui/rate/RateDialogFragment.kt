package com.g2pdev.smartrate.ui.rate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import com.g2pdev.smartrate.SmartRate
import com.g2pdev.smartrate.databinding.FragmentDialogRateBinding
import com.g2pdev.smartrate.extension.getColorCompat
import com.g2pdev.smartrate.interactor.GetAppIcon
import com.g2pdev.smartrate.logic.model.config.RateConfig
import com.g2pdev.smartrate.ui.base.BaseBottomDialogFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class RateDialogFragment : BaseBottomDialogFragment() {

    private var binding: FragmentDialogRateBinding? = null

    @Inject
    lateinit var getAppIcon: GetAppIcon

    var onRateListener: ((rating: Float) -> Unit)? = null
    var onNeverClickListener: (() -> Unit)? = null
    var onLaterClickListener: (() -> Unit)? = null

    override fun getFragmentTag() = fragmentTag

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SmartRate.plusRateComponent().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentDialogRateBinding
            .inflate(inflater, container, false)
            .also { binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val config = arguments
            ?.getParcelable<RateConfig>(ARG_CONFIG)
            ?: run {
                close()
                return
            }

        loadAppIcon()

        binding?.titleTv?.setText(config.titleResId)
        binding?.neverBtn?.setText(config.neverAskResId)
        binding?.laterBtn?.setText(config.laterResId)

        context?.getColorCompat(config.titleTextColorResId)?.let { binding?.titleTv?.setTextColor(it) }
        context?.getColorCompat(config.neverAskButtonTextColorResId)?.let { binding?.neverBtn?.setTextColor(it) }
        config.laterButtonTextColorResId?.let {
            context?.getColorCompat(it)?.let { color ->
                binding?.laterBtn?.setTextColor(color)
            }
        }

        if (!config.isDismissible) {
            disableDismiss()
        }

        setupListeners()
    }

    private fun setupListeners() {
        binding?.ratingBar?.setOnRatingBarChangeListener { _, rating, fromUser ->
            if (fromUser) {
                onRateListener?.invoke(rating)

                close()
            }
        }

        binding?.neverBtn?.setOnClickListener {
            onNeverClickListener?.invoke()

            close()
        }

        binding?.laterBtn?.setOnClickListener {
            onLaterClickListener?.invoke()

            close()
        }
    }

    override fun onDestroyView() {
        binding = null

        super.onDestroyView()
    }

    private fun loadAppIcon() {
        lifecycleScope.launchWhenCreated {
            getAppIcon
                .exec()
                .let { icon ->
                    withContext(Dispatchers.Main) {
                        binding?.iconIv?.setImageDrawable(icon)
                    }
                }
        }
    }

    private fun close() {
        onDismissListener = null

        dismiss()
    }

    companion object {

        private const val fragmentTag = "RateDialogFragment"

        private const val ARG_CONFIG = "config"

        fun newInstance(config: RateConfig): RateDialogFragment {
            return RateDialogFragment().apply {
                arguments = bundleOf(ARG_CONFIG to config)
            }
        }
    }
}
