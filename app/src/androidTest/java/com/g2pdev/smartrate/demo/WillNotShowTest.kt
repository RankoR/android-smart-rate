package com.g2pdev.smartrate.demo

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class WillNotShowTest : BaseTest() {

    @Test
    fun testDialogWillNotShow() {
        assertEquals("Invalid session count", 1, getCurrentSessionCount())

        setSessionCount(10)

        showDialog()

        assertToastWithTextDisplayed(R.string.title_rate_dialog_will_not_show)
        assertRateDialogNotDisplayed()
    }
}