package com.mindera.rocketscience.dagger.activity

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(
    val activity: AppCompatActivity
    ) {

    @Provides
    fun providesActivity(): Activity = activity
}