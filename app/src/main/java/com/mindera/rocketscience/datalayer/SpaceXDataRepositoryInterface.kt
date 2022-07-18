package com.mindera.rocketscience.datalayer

import com.mindera.rocketscience.model.AllLaunchesRespItem
import com.mindera.rocketscience.model.CompanyInfoResp
import com.mindera.rocketscience.model.USDCurrencyAmount
import retrofit2.Response

interface SpaceXDataRepositoryInterface {
    suspend fun getCompanyInfo(): Response<CompanyInfoResp>
    suspend fun getLatestUSDValue(fullUrl: String): Response<USDCurrencyAmount>
    suspend fun getAllLaunches(limit: Int, offset: Int): Response<List<AllLaunchesRespItem>>
}