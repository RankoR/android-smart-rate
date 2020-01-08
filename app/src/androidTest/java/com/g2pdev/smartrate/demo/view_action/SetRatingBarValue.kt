package com.g2pdev.smartrate.demo.view_action

import android.view.View
import android.widget.RatingBar
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import org.hamcrest.Matcher

/**
 * Warning: On Android 10+ you must enable hidden methods calls:
 * adb shell settings put global hidden_api_policy  1
 *
 * See: https://developer.android.com/distribute/best-practices/develop/restrictions-non-sdk-interfaces
 */
class SetRatingBarValue(
    private val value: Float,
    private val forceFromUser: Boolean
) : ViewAction {
    override fun getConstraints(): Matcher<View> {
        return isAssignableFrom(RatingBar::class.java)
    }

    override fun getDescription(): String {
        return "Custom view action to set rating."
    }

    override fun perform(uiController: UiController, view: View) {
        val ratingBar = view as RatingBar
        ratingBar.rating = value

        if (forceFromUser) {
            val dispatchRatingChangeMethod = RatingBar::class.java
                .getDeclaredMethod("dispatchRatingChange", Boolean::class.java)

            dispatchRatingChangeMethod.isAccessible = true

            dispatchRatingChangeMethod.invoke(ratingBar, true)
        }
    }
}
