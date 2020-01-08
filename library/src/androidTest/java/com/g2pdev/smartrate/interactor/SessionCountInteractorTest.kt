package com.g2pdev.smartrate.interactor

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.g2pdev.smartrate.BaseTest
import com.g2pdev.smartrate.interactor.session_count.GetSessionCount
import com.g2pdev.smartrate.interactor.session_count.IncrementSessionCount
import javax.inject.Inject
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class SessionCountInteractorTest : BaseTest() {

    @Inject
    lateinit var getSessionCount: GetSessionCount

    @Inject
    lateinit var incrementSessionCount: IncrementSessionCount

    @Before
    fun setUp() {
        createDaggerComponent()
            .inject(this)
    }

    @Test
    fun testSessionCount() {
        getSessionCount
            .exec()
            .test()
            .assertValue(0)

        incrementSessionCount
            .exec()
            .test()
            .assertComplete()

        incrementSessionCount
            .exec()
            .test()
            .assertComplete()

        getSessionCount
            .exec()
            .test()
            .assertValue(2)
    }
}
