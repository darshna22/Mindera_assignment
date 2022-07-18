package com.mindera.rocketscience.datalayer

import com.mindera.rocketscience.retofit.RetrofitService
import com.mindera.rocketscience.retofit.result
import javax.inject.Inject


class SpaceXDataRepositoryImpl @Inject constructor(val retrofitService: RetrofitService) :
    SpaceXDataRepositoryInterface {

    override suspend fun getCompanyInfo() = retrofitService.getCompanyInfo()

    override suspend fun getLatestUSDValue(fullUrl: String) =
        retrofitService.getConvertedUSDValue(fullUrl)

    override suspend fun getAllLaunches(limit: Int, offset: Int) =
        retrofitService.getAllLaunches(limit = limit, offset = offset)


}