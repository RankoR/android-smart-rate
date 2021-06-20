package com.g2pdev.smartrate.ui.base

import android.content.DialogInterface
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import com.g2pdev.smartrate.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

internal abstract class BaseBottomDialogFragment : BottomSheetDialogFragment() {

    var onDismissListener: (() -> Unit)? = null

    protected abstract fun getFragmentTag(): String

    override fun getTheme(): Int = R.style.AppBottomSheetDialogTheme

    fun show(activity: FragmentActivity?) {
        activity?.supportFragmentManager?.let { fragmentManager ->
            if (fragmentManager.findFragmentByTag(getFragmentTag()) == null && !activity.isFinishing) {
                show(fragmentManager, getFragmentTag())
            }
        }
    }

    override fun onStart() {
        super.onStart()

        BottomSheetBehavior
            .from(requireView().parent as View)
            .apply {
                state = BottomSheetBehavior.STATE_EXPANDED
            }
    }

    override fun onDismiss(dialog: DialogInterface) {
        onDismissListener?.invoke()

        super.onDismiss(dialog)
    }

    fun disableDismiss(duration: Long = 0L, dismissEnabledListener: (() -> Unit)? = null) {
        isCancelable = false

        if (duration > 0) {
            lifecycleScope.launch {
                delay(duration)

                isCancelable = true
                dismissEnabledListener?.invoke()
            }
        }
    }
}
