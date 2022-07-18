package com.mindera.rocketscience.dagger

import com.mindera.rocketscience.datalayer.SpaceXDataRepositoryInterface
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [MyAppModule::class, SpaceXDataModule::class, RetrofitModule::class])
interface MyAppComponent {

    val spaceXDataRepositoryInterface: SpaceXDataRepositoryInterface
}

