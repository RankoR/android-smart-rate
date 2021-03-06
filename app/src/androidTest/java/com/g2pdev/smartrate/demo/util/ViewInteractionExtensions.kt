package com.g2pdev.smartrate.demo.util

import android.view.View
import android.widget.TextView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import java.util.concurrent.TimeoutException
import junit.framework.AssertionFailedError
import org.hamcrest.Matcher

/**
 * Get text from the given view interaction (works only on TextView and its descendants)
 */
internal fun ViewInteraction.getText(): String {
    var text = ""

    perform(object : ViewAction {
        override fun getConstraints(): Matcher<View> {
            return ViewMatchers.isAssignableFrom(TextView::class.java)
        }

        override fun getDescription(): String {
            return "Text of the view"
        }

        override fun perform(uiController: UiController, view: View) {
            val tv = view as TextView
            text = tv.text.toString()
        }
    })

    return text
}

/**
 * Waits until view in given ViewInteraction become visible
 *
 * @param timeout Maximum wait timeout, default = 10 000 ms
 */
internal fun ViewInteraction.waitUntilVisible(timeout: Long = 10_000): ViewInteraction {
    val startTime = System.currentTimeMillis()
    val endTime = startTime + timeout

    do {
        try {
            check(matches(isDisplayed()))
            return this
        } catch (e: NoMatchingViewException) {
            Thread.sleep(50)
        }
    } while (System.currentTimeMillis() < endTime)

    throw TimeoutException()
}

/**
 * Waits until view in given ViewInteraction disappears in root
 *
 * @param timeout Maximum wait timeout, default = 10 000 ms
 */
internal fun ViewInteraction.waitUntilDoesNotExist(timeout: Long = 10_000): ViewInteraction {
    val startTime = System.currentTimeMillis()
    val endTime = startTime + timeout

    do {
        try {
            check(doesNotExist())
            return this
        } catch (e: AssertionFailedError) {
            Thread.sleep(50)
        }
    } while (System.currentTimeMillis() < endTime)

    throw TimeoutException()
}
