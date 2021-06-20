package com.g2pdev.smartrate.demo

import android.app.Activity
import androidx.annotation.StringRes
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import com.g2pdev.smartrate.demo.presentation.MainActivity
import com.g2pdev.smartrate.demo.util.getText
import com.g2pdev.smartrate.demo.util.waitUntilDoesNotExist
import com.g2pdev.smartrate.demo.util.waitUntilVisible
import com.g2pdev.smartrate.demo.view_action.SetRatingBarValue
import java.util.regex.Pattern
import org.hamcrest.Matchers.`is`
import org.junit.Assert.assertEquals
import org.junit.Rule

abstract class BaseUiTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    protected fun sleep(ms: Long) {
        Thread.sleep(ms)
    }

    protected fun getCurrentSessionCount(): Int {
        val text = onView(withId(R.id.fakeSessionCountTv)).getText()

        val numberPattern = Pattern.compile("(\\d+)")
        val matcher = numberPattern.matcher(text)
        if (matcher.find()) {
            return matcher.group().toInt()
        }

        throw IllegalStateException("No number found")
    }

    protected fun setLibrarySessionCount(sessionCount: Int) {
        assertEquals("Invalid session count", 1, getCurrentSessionCount())

        setSessionCount(sessionCount)

        while (getCurrentSessionCount() < sessionCount) {
            incrementSessionCount()
        }
    }

    protected fun setSessionCount(sessionCount: Int) {
        onView(withId(R.id.sessionCountEt)).perform(replaceText(sessionCount.toString()))
    }

    protected fun setSessionCountBetweenPrompts(sessionCount: Int) {
        onView(withId(R.id.sessionCountBetweenPromptsEt)).perform(replaceText(sessionCount.toString()))
    }

    protected fun incrementSessionCount() {
        onView(withId(R.id.incrementFakeSessionCountBtn))
            .waitUntilVisible()
            .perform(click())
    }

    protected fun setRating(rating: Float) {
        sleep(3_000)

        onView(withId(R.id.ratingBar))
            .waitUntilVisible()
            .perform(SetRatingBarValue(rating, true))
    }

    protected fun showDialog() {
        onView(withId(R.id.showRateDialogBtn))
            .waitUntilVisible()
            .perform(click())
    }

    protected fun resetLibraryCounters() {
        onView(withId(R.id.resetLibraryCountersBtn))
            .waitUntilVisible()
            .perform(click())
    }

    protected fun clickRateDialogNever() {
        sleep(3_000)

        onView(withId(R.id.neverBtn))
            .waitUntilVisible()
            .perform(click())
    }

    protected fun clickRateDialogLater() {
        sleep(3_000)

        onView(withId(R.id.laterBtn))
            .waitUntilVisible()
            .perform(click())
    }

    private fun getLogsText(): String {
        return onView(withId(R.id.logsTv))
            .inRoot(withDecorView(`is`(getActivity()!!.window.decorView)))
            .getText()
    }

    private fun getLastLogEntry(): String {
        return getLogsText()
            .split("\n")
            .lastOrNull() ?: ""
    }

    protected fun assertLastLogEntry(@StringRes textResId: Int) {
        assertLastLogEntry(getString(textResId))
    }

    protected fun assertLogEntries(@StringRes vararg textResIds: Int) {
        val text = textResIds.joinToString("\n", transform = ::getString)
        val logsText = getLogsText().trim()

        assertEquals(text, logsText)
    }

    protected fun assertLogEntries(vararg lines: String) {
        val text = lines.joinToString("\n")
        val logsText = getLogsText().trim()

        assertEquals(text, logsText)
    }

    protected fun assertLastLogEntry(text: String) {
        assertEquals("Last log entry does not match", text, getLastLogEntry())
    }

    protected fun assertRateDialogDisplayed() {
        onView(withId(R.id.ratingBar))
            .waitUntilVisible()
            .check(matches(isDisplayed()))
    }

    protected fun assertRateDialogNotDisplayed() {
        onView(withId(R.id.ratingBar))
            .waitUntilDoesNotExist()
            .check(doesNotExist())
    }

    protected fun assertFeedbackDialogDisplayed() {
        onView(withId(R.id.feedbackEt))
            .waitUntilVisible()
            .check(matches(isDisplayed()))
    }

    protected fun assertFeedbackDialogNotDisplayed() {
        onView(withId(R.id.feedbackEt))
            .waitUntilDoesNotExist()
            .check(doesNotExist())
    }

    private fun getActivity(): Activity? {
        var activity: Activity? = null
        activityRule.scenario.onActivity {
            activity = it
        }
        return activity
    }

    protected fun getString(@StringRes resId: Int): String {
        return InstrumentationRegistry.getInstrumentation().targetContext.getString(resId)
    }

    protected fun getString(@StringRes resId: Int, vararg formatArgs: Any): String {
        return InstrumentationRegistry.getInstrumentation().targetContext.getString(
            resId,
            *formatArgs
        )
    }
}
