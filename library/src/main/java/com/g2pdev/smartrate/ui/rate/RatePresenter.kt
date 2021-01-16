package com.g2pdev.smartrate.ui.rate

import android.graphics.drawable.Drawable
import com.g2pdev.smartrate.SmartRate
import com.g2pdev.smartrate.extension.schedulersIoToMain
import com.g2pdev.smartrate.interactor.GetAppIcon
import com.g2pdev.smartrate.ui.base.BasePresenter
import javax.inject.Inject
import moxy.InjectViewState
import timber.log.Timber

@InjectViewState
internal class RatePresenter : BasePresenter<RateView>() {

    @Inject
    lateinit var getAppIcon: GetAppIcon

    init {
        if (SmartRate.instance == null) {
            throw IllegalStateException("Not initialized")
        }
        SmartRate.instance!!.plusRateComponent().inject(this)
    }

    fun loadAppIcon(overrideIcon: Drawable?) {
        overrideIcon
            ?.let(viewState::showAppIcon)
            ?: run {
                getAppIcon
                    .exec()
                    .schedulersIoToMain()
                    .subscribe(viewState::showAppIcon, Timber::e)
                    .disposeOnDestroy()
            }
    }

    fun onRated(rating: Float) {
        viewState.close()
    }

    fun onNeverClick() {
        viewState.close()
    }

    fun onLaterClick() {
        viewState.close()
    }
}
