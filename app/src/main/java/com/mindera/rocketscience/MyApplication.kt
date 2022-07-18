package com.mindera.rocketscience

import android.app.Application
import com.mindera.rocketscience.dagger.DaggerMyAppComponent
import com.mindera.rocketscience.dagger.MyAppComponent
import com.mindera.rocketscience.dagger.MyAppModule

class MyApplication : Application() {

    lateinit var appComponent: MyAppComponent
    private set

    override fun onCreate() {
        super.onCreate()

        appComponent = initDagger(this)
    }

    private fun initDagger(app: MyApplication): MyAppComponent =
        DaggerMyAppComponent.builder()
            .myAppModule(MyAppModule(app))
            .build()
}