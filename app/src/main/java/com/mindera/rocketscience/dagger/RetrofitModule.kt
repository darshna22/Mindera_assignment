package com.mindera.rocketscience.dagger

import com.mindera.rocketscience.retofit.RetrofitBuilder
import com.mindera.rocketscience.retofit.RetrofitService
import dagger.Module
import dagger.Provides

@Module
class RetrofitModule {

    @Provides
    fun provideRetrofitService(retrofitBuilder: RetrofitBuilder): RetrofitService =
        retrofitBuilder.buildAPI(RetrofitService::class.java)
}