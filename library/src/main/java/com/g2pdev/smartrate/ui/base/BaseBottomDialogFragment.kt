package com.g2pdev.smartrate.ui.base

import android.content.DialogInterface
import android.view.View
import androidx.fragment.app.FragmentActivity
import com.g2pdev.smartrate.R
import com.g2pdev.smartrate.extension.schedulersIoToMain
import com.google.android.material.bottomsheet.BottomSheetBehavior
import io.reactivex.Completable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit
import moxy.MvpBottomSheetDialogFragment
import timber.log.Timber

internal abstract class BaseBottomDialogFragment : MvpBottomSheetDialogFragment() {

    private val compositeDisposable by lazy { CompositeDisposable() }

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

    override fun onDestroyView() {
        compositeDisposable.clear()

        super.onDestroyView()
    }

    fun disableDismiss(duration: Long = 0L, dismissEnabledListener: (() -> Unit)? = null) {
        isCancelable = false

        if (duration > 0) {
            Completable
                .timer(duration, TimeUnit.MILLISECONDS)
                .schedulersIoToMain()
                .subscribe({
                    isCancelable = true

                    dismissEnabledListener?.invoke()
                }, Timber::e)
                .disposeOnDestroy()
        }
    }

    protected fun Disposable.disposeOnDestroy() {
        compositeDisposable.add(this)
    }
}
