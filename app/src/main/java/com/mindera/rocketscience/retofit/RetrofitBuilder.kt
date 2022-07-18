package com.mindera.rocketscience.retofit

import android.content.Context
import com.mindera.rocketscience.utility.CheckValidation
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RetrofitBuilder @Inject constructor(val mContext: Context) {
    val baseUrl = "https://api.spacexdata.com/v3/"

    fun <T> buildAPI(api: Class<T>): T {
        return getService(api)
    }

    /*This class return api service class instance eg:RetrofitService etc*/
    private fun <T> getService(api: Class<T>): T {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient(context = mContext))
            .build().create(api)
    }

    private fun okHttpClient(context: Context): OkHttpClient {
        val cashSize: Long = (5 * 1024 * 1024).toLong()
        val cache = Cache(context.cacheDir, cashSize)
        return OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor { chain: Interceptor.Chain ->
                var requst = chain.request()
                requst = if (CheckValidation.isConnected(context)) {
                    requst
                        .newBuilder()
                        .header("Cache-Control", "public, max-age=" + 5)
                        .build()
                } else {
                    requst
                        .newBuilder()
                        .header(
                            "Cache-Control",
                            "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7
                        )
                        .build()
                }
                chain.proceed(requst)
            }
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .build()
    }

}