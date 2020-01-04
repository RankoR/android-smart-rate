package com.g2pdev.smartrate

import androidx.test.platform.app.InstrumentationRegistry
import com.g2pdev.smartrate.di.DaggerTestRateComponent
import com.g2pdev.smartrate.di.TestRateComponent
import com.g2pdev.smartrate.di.TestRateModule

internal abstract class BaseTest {

    protected fun createDaggerComponent(): TestRateComponent {
        return DaggerTestRateComponent
            .builder()
            .testRateModule(TestRateModule(InstrumentationRegistry.getInstrumentation().targetContext))
            .build()
    }

}