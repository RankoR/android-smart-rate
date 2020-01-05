package com.g2pdev.smartrate.ui.feedback

import com.g2pdev.smartrate.ui.base.BasePresenter
import moxy.InjectViewState

@InjectViewState
internal class FeedbackPresenter : BasePresenter<FeedbackView>() {

    private var minFeedbackTextLength = 0

    fun setMinFeedbackLength(length: Int) {
        this.minFeedbackTextLength = length
    }

    fun onFeedbackTextChanged(text: String) {
        val isSubmitButtonEnabled = text.length >= minFeedbackTextLength

        viewState.enableSubmitButton(isSubmitButtonEnabled)
    }

}