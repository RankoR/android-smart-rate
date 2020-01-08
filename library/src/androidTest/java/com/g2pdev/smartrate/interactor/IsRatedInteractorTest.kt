package com.g2pdev.smartrate.interactor

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.g2pdev.smartrate.BaseTest
import com.g2pdev.smartrate.interactor.is_rated.IsRated
import com.g2pdev.smartrate.interactor.is_rated.SetIsRated
import javax.inject.Inject
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class IsRatedInteractorTest : BaseTest() {

    @Inject
    lateinit var isRated: IsRated

    @Inject
    lateinit var setIsRated: SetIsRated

    @Before
    fun setUp() {
        createDaggerComponent()
            .inject(this)
    }

    @Test
    fun testIsRated() {
        isRated
            .exec()
            .test()
            .assertValue(false)

        setIsRated
            .exec(true)
            .test()
            .assertComplete()

        isRated
            .exec()
            .test()
            .assertValue(true)
    }
}
