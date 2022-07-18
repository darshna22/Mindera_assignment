package com.mindera.uilayer

import org.junit.Assert
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
}