package com.g2pdev.smartrate.demo

import android.view.View
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.g2pdev.smartrate.demo.matcher.ToastMatcher
import com.g2pdev.smartrate.demo.ui.MainActivity
import org.hamcrest.Matcher
import org.hamcrest.Matchers.not
import org.junit.Rule
import java.util.regex.Pattern

abstract class BaseTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    protected fun sleep(ms: Long) {
        Thread.sleep(ms)
    }

    protected fun getCurrentSessionCount(): Int {
        val text = getText(onView(withId(R.id.fakeSessionCountTv)))

        val numberPattern = Pattern.compile("(\\d+)")
        val matcher = numberPattern.matcher(text)
        if (matcher.find()) {
            return matcher.group().toInt()
        }

        throw IllegalStateException("No number found")
    }

    protected fun setSessionCount(sessionCount: Int) {
        onView(withId(R.id.sessionCountEt)).perform(replaceText(sessionCount.toString()))
    }

    protected fun setSessionCountBetweenPrompts(sessionCount: Int) {
        onView(withId(R.id.sessionCountBetweenPromptsEt)).perform(replaceText(sessionCount.toString()))
    }

    protected fun incrementSessionCount() {

    }

    protected fun showDialog() {
        onView(withId(R.id.showRateDialogBtn)).perform(click())
    }

    protected fun resetLibraryCounters() {

    }

    private fun getToastWithText(@StringRes textResId: Int): ViewInteraction {
        return onView(withText(textResId)).inRoot(ToastMatcher())
    }

    protected fun assertToastWithTextDisplayed(@StringRes textResId: Int) {
        getToastWithText(textResId).check(matches(isDisplayed()))
    }

    protected fun assertToastWithTextNotDisplayed(@StringRes textResId: Int) {
        getToastWithText(textResId).check(matches(not(isDisplayed())))
    }

    protected fun assertRateDialogDisplayed() {
        onView(withId(R.id.ratingBar)).check(matches(isDisplayed()))
    }

    protected fun assertRateDialogNotDisplayed() {
        onView(withId(R.id.ratingBar)).check(doesNotExist())
    }

    protected fun getText(matcher: ViewInteraction): String {
        var text = String()
        matcher.perform(object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return isAssignableFrom(TextView::class.java)
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

}