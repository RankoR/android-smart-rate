package com.g2pdev.smartrate.demo

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.g2pdev.smartrate.demo.ui.MainActivity
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class ExampleInstrumentedTest {

    @get:Rule
    var activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(
        MainActivity::class.java
    )

    private fun checkIfDialogDisplayed() {
        onView(withId(R.id.ratingBar)).check(matches(isDisplayed()))
    }

    private fun checkIfDialogNotDisplayed() {
        onView(withId(R.id.ratingBar)).check(doesNotExist())
    }

    @Test
    fun test() {
        // TODO
        assertTrue(true)
    }
}