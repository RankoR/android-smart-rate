package com.g2pdev.smartrate.demo

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Matchers.not
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class FeedbackDialogTest : BaseUiTest() {

    @Test
    fun testShowFeedbackDialog() {
        setLibrarySessionCount(3)

        showDialog()
        assertRateDialogDisplayed()

        setRating(3f)

        assertRateDialogNotDisplayed()
        assertFeedbackDialogDisplayed()
    }

    @Test
    fun testFeedbackDialogCancel() {
        setLibrarySessionCount(3)

        showDialog()
        assertRateDialogDisplayed()

        setRating(3f)

        assertRateDialogNotDisplayed()
        assertFeedbackDialogDisplayed()

        sleep(3_000)
        onView(withId(R.id.cancelBtn)).perform(click())

        assertFeedbackDialogNotDisplayed()

        assertLogEntries(
            getString(R.string.title_rate_dialog_shown),
            getString(R.string.format_rated, 3f),
            getString(R.string.title_feedback_cancel_clicked)
        )
    }

    @Test
    fun testFeedbackDialogSubmitButtonEnableOrDisable() {
        setLibrarySessionCount(3)

        showDialog()
        assertRateDialogDisplayed()

        setRating(3f)

        assertRateDialogNotDisplayed()
        assertFeedbackDialogDisplayed()

        sleep(3_000)

        onView(withId(R.id.submitBtn)).check(matches(not(isEnabled())))

        onView(withId(R.id.feedbackEt)).perform(typeText("123"))
        onView(withId(R.id.submitBtn)).check(matches(not(isEnabled())))

        onView(withId(R.id.feedbackEt)).perform(typeText("456"))
        onView(withId(R.id.submitBtn)).check(matches(isEnabled()))

        onView(withId(R.id.feedbackEt)).perform(replaceText("12345"))
        onView(withId(R.id.submitBtn)).check(matches(not(isEnabled())))
    }

    @Test
    fun testFeedbackDialogSubmit() {
        setLibrarySessionCount(3)

        showDialog()
        assertRateDialogDisplayed()

        setRating(3f)

        assertRateDialogNotDisplayed()
        assertFeedbackDialogDisplayed()

        sleep(3_000)

        onView(withId(R.id.feedbackEt)).perform(replaceText("123456"))
        onView(withId(R.id.submitBtn)).perform(click())

        assertFeedbackDialogNotDisplayed()

        assertLogEntries(
            getString(R.string.title_rate_dialog_shown),
            getString(R.string.format_rated, 3f),
            getString(R.string.format_feedback_submit_clicked, "123456")
        )
    }
}