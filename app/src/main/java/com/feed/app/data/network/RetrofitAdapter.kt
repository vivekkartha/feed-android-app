package com.feed.app.data.network

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitAdapter {
  private var webservice: WebService? = null
  private const val endPoint = "https://dl.dropboxusercontent.com/"

  fun getService(): WebService {
    if (webservice == null) {
      configRestAdapter(endPoint)
    }
    return webservice!!
  }

  private fun configRestAdapter(host: String) {
    val okHttpClient: OkHttpClient
    val okBuilder: OkHttpClient.Builder
    val loggingInterceptor = HttpLoggingInterceptor()
    // set your desired log level
    loggingInterceptor.level = HttpLoggingInterceptor.Level.NONE

    okBuilder = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
    okHttpClient = okBuilder
        .connectTimeout(60, TimeUnit.SECONDS)
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl(host)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(okHttpClient)
        .build()
    webservice = retrofit.create(WebService::class.java)
  }
}