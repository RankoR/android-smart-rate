package com.g2pdev.smartrate.interactor

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.g2pdev.smartrate.BaseTest
import com.g2pdev.smartrate.cache.SessionCountCache
import com.g2pdev.smartrate.interactor.last_prompt.GetLastPromptSession
import com.g2pdev.smartrate.interactor.last_prompt.SetLastPromptSessionToCurrent
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
internal class LastPromptInteractorTest : BaseTest() {

    @Inject
    lateinit var getLastPromptSession: GetLastPromptSession

    @Inject
    lateinit var setLastPromptSessionToCurrent: SetLastPromptSessionToCurrent

    @Inject
    lateinit var sessionCountCache: SessionCountCache

    @Before
    fun setUp() {
        createDaggerComponent()
            .inject(this)
    }

    @Test
    fun testLastPromptInteractorTest() {
        getLastPromptSession
            .exec()
            .test()
            .assertValue(0)

        sessionCountCache
            .put(10)
            .test()
            .assertComplete()

        setLastPromptSessionToCurrent
            .exec()
            .test()
            .assertComplete()

        getLastPromptSession
            .exec()
            .test()
            .assertValue(10)
    }

}