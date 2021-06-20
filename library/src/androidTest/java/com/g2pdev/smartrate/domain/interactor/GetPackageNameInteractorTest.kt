package com.g2pdev.smartrate.domain.interactor

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.g2pdev.smartrate.BaseTest
import javax.inject.Inject
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class GetPackageNameInteractorTest : BaseTest() {

    @Inject
    lateinit var getPackageName: GetPackageName

    @Before
    fun setUp() {
        createDaggerComponent().inject(this)
    }

    @Test
    fun testGetPackageName() {
        Assert.assertEquals(PACKAGE_NAME, getPackageName.exec())
    }

    private companion object {
        private const val PACKAGE_NAME = "com.g2pdev.smartrate.test"
    }
}
