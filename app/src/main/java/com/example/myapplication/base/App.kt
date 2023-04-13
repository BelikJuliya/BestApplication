package com.example.myapplication.base

import android.app.Application
import com.example.data.remote.FilmsService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://imdb-api.com/"
class App : Application() {

    lateinit var apiService: FilmsService

    override fun onCreate() {
        super.onCreate()
        initRetrofit()
    }

    private fun initRetrofit() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://your.api.url/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(FilmsService::class.java)
    }
}