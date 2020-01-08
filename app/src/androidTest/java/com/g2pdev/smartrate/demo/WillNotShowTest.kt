package com.g2pdev.smartrate.demo

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Test scenarios when the dialog will not be shown
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class WillNotShowTest : BaseUiTest() {

    @Test
    fun testNotEnoughSessions() {
        setSessionCount(3)

        showDialog()

        assertRateDialogNotDisplayed()

        assertLastLogEntry(R.string.title_rate_dialog_will_not_show)
    }

    @Test
    fun testNeverClicked() {
        setLibrarySessionCount(3)

        showDialog()
        assertRateDialogDisplayed()

        clickRateDialogNever()
        assertRateDialogNotDisplayed()

        showDialog()
        assertRateDialogNotDisplayed()

        assertLogEntries(
            R.string.title_rate_dialog_shown,
            R.string.title_never_clicked,
            R.string.title_rate_dialog_will_not_show
        )
    }

    @Test
    fun testMaybeLaterNotEnoughSessions() {
        setLibrarySessionCount(3)
        setSessionCountBetweenPrompts(3)

        showDialog()
        assertRateDialogDisplayed()

        clickRateDialogLater()
        assertRateDialogNotDisplayed()

        showDialog()
        assertRateDialogNotDisplayed()

        assertLogEntries(
            R.string.title_rate_dialog_shown,
            R.string.title_later_clicked,
            R.string.title_rate_dialog_will_not_show
        )
    }

}