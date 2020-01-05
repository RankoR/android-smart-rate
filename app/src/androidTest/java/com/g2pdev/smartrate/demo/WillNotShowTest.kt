package com.g2pdev.smartrate.demo

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class WillNotShowTest : BaseTest() {

    @Test
    fun testNotEnoughSessions() {
        assertEquals("Invalid session count", 1, getCurrentSessionCount())

        setSessionCount(10)

        showDialog()

        assertToastWithTextDisplayed(R.string.title_rate_dialog_will_not_show)
        assertRateDialogNotDisplayed()
    }

    @Test
    fun testNeverClicked() {
        assertEquals("Invalid session count", 1, getCurrentSessionCount())

        val desiredSessionCount = 3

        setSessionCount(desiredSessionCount)

        while (getCurrentSessionCount() < desiredSessionCount) {
            incrementSessionCount()
        }

        showDialog()
        assertRateDialogDisplayed()

        clickRateDialogNever()
        assertRateDialogNotDisplayed()

        showDialog()
        assertRateDialogNotDisplayed()
    }

    @Test
    fun testMaybeLaterNotEnoughSessions() {
        assertEquals("Invalid session count", 1, getCurrentSessionCount())

        val desiredSessionCount = 3

        setSessionCount(desiredSessionCount)
        setSessionCountBetweenPrompts(3)

        while (getCurrentSessionCount() < desiredSessionCount) {
            incrementSessionCount()
        }

        showDialog()
        assertRateDialogDisplayed()

        clickRateDialogLater()
        assertRateDialogNotDisplayed()

        showDialog()
        assertRateDialogNotDisplayed()
    }

}