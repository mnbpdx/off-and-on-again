package com.example.off_and_on_again_android

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.*

private const val BASE_URL = "http://192.168.1.23:5000/"

private val gson = GsonBuilder()
    .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
    .create()

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create(gson))
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .build()

interface RaspberryPiApiService {
    @GET("switches/{switchId}")
    suspend fun getSwitch(@Path("switchId") switchId: String): SwitchResponse
}

object RaspberryPiApi {
    val retrofitService: RaspberryPiApiService by lazy { retrofit.create(RaspberryPiApiService::class.java) }
}
