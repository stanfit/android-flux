package com.awesome.deux.di

import android.content.Context
import com.awesome.deux.BuildConfig
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.readystatesoftware.chuck.ChuckInterceptor
import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

/**
 * Module of network.
 */
object NetworkModule {

    /**
     * Provide OkHttpClient
     *
     * @param context Context
     * @return OkHttpClient
     */
    fun provideOkHttpClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .apply {
                if (BuildConfig.DEBUG) {
                    addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BASIC
                    })
                    addInterceptor(ChuckInterceptor(context))
                }
            }
            .build()
    }

    /**
     * Provide Retrofit API
     *
     * @param httpClient OkHttpClient
     * @return T
     */
    inline fun <reified T> provideRetrofitApi(httpClient: OkHttpClient): T {
        val contentType = "application/json".toMediaType()
        val retrofit = Retrofit.Builder()
            .addConverterFactory(Json.nonstrict.asConverterFactory(contentType))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .baseUrl(BuildConfig.API_BASE_URL)
            .client(httpClient)
            .build()
        return retrofit.create(T::class.java)
    }
}