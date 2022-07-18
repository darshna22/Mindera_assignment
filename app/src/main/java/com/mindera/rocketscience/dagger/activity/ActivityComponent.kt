package com.mindera.rocketscience.dagger.activity

import android.app.Activity
import com.mindera.rocketscience.dagger.MyAppComponent
import com.mindera.uilayer.MainActivity
import dagger.Component

@ActivityScope
@Component(modules = [ActivityModule::class], dependencies = [MyAppComponent::class])
interface ActivityComponent {
    fun inject(mainActivity: MainActivity)
    val activity: Activity
}

