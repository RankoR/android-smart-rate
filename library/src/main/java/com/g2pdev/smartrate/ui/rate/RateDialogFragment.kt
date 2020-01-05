package com.g2pdev.smartrate.ui.rate

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.g2pdev.smartrate.R
import com.g2pdev.smartrate.extension.getColorCompat
import com.g2pdev.smartrate.extension.getDrawableCompat
import com.g2pdev.smartrate.logic.model.config.RateConfig
import com.g2pdev.smartrate.ui.base.BaseBottomDialogFragment
import kotlinx.android.synthetic.main.fragment_dialog_rate.*
import moxy.presenter.InjectPresenter

internal class RateDialogFragment : BaseBottomDialogFragment(), RateView {

    @InjectPresenter
    lateinit var presenter: RatePresenter

    var onRateListener: ((rating: Float) -> Unit)? = null
    var onNeverClickListener: (() -> Unit)? = null
    var onLaterClickListener: (() -> Unit)? = null

    override fun getFragmentTag() = fragmentTag

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dialog_rate, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments
            ?.getParcelable<RateConfig>(argConfig)
            ?.let { config ->

                val fallbackIconDrawable = context?.getDrawableCompat(R.drawable.star)
                val overrideIconDrawable = config.iconDrawableResId?.let { context?.getDrawableCompat(it) }

                presenter.loadAppIcon(overrideIconDrawable, fallbackIconDrawable)

                titleTv.setText(config.titleResId)
                neverBtn.setText(config.neverAskResId)
                laterBtn.setText(config.laterResId)

                context?.getColorCompat(config.titleTextColorResId)?.let(titleTv::setTextColor)
                context?.getColorCompat(config.neverAskButtonTextColorResId)
                    ?.let(neverBtn::setTextColor)
                config.laterButtonTextColorResId?.let {
                    context?.getColorCompat(it)?.let(laterBtn::setTextColor)
                }

                if (!config.isDismissible) {
                    disableDismiss()
                }

            } ?: run(::close)

        ratingBar.setOnRatingBarChangeListener { _, rating, fromUser ->
            if (fromUser) {
                onRateListener?.invoke(rating)

                presenter.onRated(rating)
            }
        }

        neverBtn.setOnClickListener {
            onNeverClickListener?.invoke()

            presenter.onNeverClick()
        }

        laterBtn.setOnClickListener {
            onLaterClickListener?.invoke()

            presenter.onLaterClick()
        }
    }

    override fun showAppIcon(drawable: Drawable) {
        iconIv.setImageDrawable(drawable)
    }

    override fun close() {
        onDismissListener = null

        dismiss()
    }

    companion object {

        private const val fragmentTag = "RateDialogFragment"

        private const val argConfig = "config"

        fun newInstance(config: RateConfig): RateDialogFragment {
            return RateDialogFragment()
                .apply {
                    arguments = bundleOf(argConfig to config)
                }
        }

    }
}