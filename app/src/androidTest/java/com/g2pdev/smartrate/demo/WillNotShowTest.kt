package com.g2pdev.smartrate.demo

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class WillNotShowTest : BaseUiTest() {

    @Test
    fun testNotEnoughSessions() {
        setLibrarySessionCount(3)

        showDialog()

        assertToastWithTextDisplayed(R.string.title_rate_dialog_will_not_show)
        assertRateDialogNotDisplayed()
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
    }

}