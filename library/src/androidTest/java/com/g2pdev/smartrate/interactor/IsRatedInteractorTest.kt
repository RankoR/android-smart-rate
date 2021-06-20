package com.g2pdev.smartrate.interactor

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.g2pdev.smartrate.BaseTest
import com.g2pdev.smartrate.interactor.is_rated.IsRated
import com.g2pdev.smartrate.interactor.is_rated.SetIsRated
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
internal class IsRatedInteractorTest : BaseTest() {

    @Inject
    lateinit var isRated: IsRated

    @Inject
    lateinit var setIsRated: SetIsRated

    @Before
    fun setUp() {
        createDaggerComponent().inject(this)
    }

    @Test
    fun testIsRated() {
        Assert.assertFalse(isRated.exec())

        runBlocking {
            setIsRated.exec(true)
        }

        Assert.assertTrue(isRated.exec())
    }
}
