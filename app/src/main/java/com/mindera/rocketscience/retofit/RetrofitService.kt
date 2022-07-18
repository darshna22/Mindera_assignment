package com.mindera.rocketscience.retofit

import com.mindera.rocketscience.model.AllLaunchesRespItem
import com.mindera.rocketscience.model.CompanyInfoResp
import com.mindera.rocketscience.model.USDCurrencyAmount
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface RetrofitService {

    @GET("launches")
    suspend fun getAllLaunches(
        @Query("limit") limit: Int, @Query("offset") offset: Int
    ): Response<List<AllLaunchesRespItem>>

    @GET("info")
    suspend fun getCompanyInfo(): Response<CompanyInfoResp>

    @Headers("apikey:kPCC3Y0G0oql7839qzrZKNSYHzXPOhjn")
    @GET
    suspend fun getConvertedUSDValue(@Url baseUrl: String): Response<USDCurrencyAmount>

}