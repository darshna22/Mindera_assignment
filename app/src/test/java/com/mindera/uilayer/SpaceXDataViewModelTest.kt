package com.mindera.uilayer

import com.mindera.rocketscience.BuildConfig
import org.junit.Assert
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class SpaceXDataViewModelTest {
    lateinit var spaceXDataViewModel: SpaceXDataViewModel

    @Test
    fun add() {
        val a = 5
        Assert.assertEquals(5, a)
    }

    @Test fun test() {
        assertTrue(BuildConfig.DEBUG)
    }
}