package com.g2pdev.smartrate.ui.feedback

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.g2pdev.smartrate.ui.base.BaseViewModel

class FeedbackDialogViewModel : BaseViewModel() {

    var minFeedbackLength = DEFAULT_MIN_FEEDBACK_LENGTH

    val feedbackText = MutableLiveData("")

    val isSubmitButtonEnabled: LiveData<Boolean> = Transformations.map(feedbackText) { it.length >= minFeedbackLength }

    private companion object {
        private const val DEFAULT_MIN_FEEDBACK_LENGTH = 16
    }
}
