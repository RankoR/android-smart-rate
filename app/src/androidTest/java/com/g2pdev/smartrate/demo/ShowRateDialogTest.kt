package com.g2pdev.smartrate.demo

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class ShowRateDialogTest : BaseUiTest() {

    @Test
    fun testShowRateDialog() {
        setLibrarySessionCount(3)

        showDialog()
        assertRateDialogDisplayed()
    }

}