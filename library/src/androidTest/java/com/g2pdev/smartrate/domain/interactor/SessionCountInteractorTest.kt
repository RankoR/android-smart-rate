package com.g2pdev.smartrate.domain.interactor

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.g2pdev.smartrate.BaseTest
import com.g2pdev.smartrate.domain.interactor.session_count.GetSessionCount
import com.g2pdev.smartrate.domain.interactor.session_count.IncrementSessionCount
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
internal class SessionCountInteractorTest : BaseTest() {

    @Inject
    lateinit var getSessionCount: GetSessionCount

    @Inject
    lateinit var incrementSessionCount: IncrementSessionCount

    @Before
    fun setUp() {
        createDaggerComponent().inject(this)
    }

    @Test
    fun testSessionCount() {
        Assert.assertEquals(0, getSessionCount.exec())

        runBlocking {
            incrementSessionCount.exec()
            incrementSessionCount.exec()
        }

        Assert.assertEquals(2, getSessionCount.exec())
    }
}
