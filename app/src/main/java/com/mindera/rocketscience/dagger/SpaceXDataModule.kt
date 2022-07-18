package com.mindera.rocketscience.dagger

import com.mindera.rocketscience.datalayer.SpaceXDataRepositoryImpl
import com.mindera.rocketscience.datalayer.SpaceXDataRepositoryInterface
import dagger.Binds
import dagger.Module

@Module
abstract class SpaceXDataModule {
    @Binds
   abstract fun provideSpaceXDataViewModel(spaceXDataRepository: SpaceXDataRepositoryImpl): SpaceXDataRepositoryInterface
}