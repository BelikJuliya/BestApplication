package com.example.myapplication.base

import android.app.Application
import androidx.room.Room
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.data.db.FilmDataBase
import com.example.data.db.FilmsDao
import com.example.data.remote.FilmsService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://imdb-api.com/"

class App : Application() {


    lateinit var apiService: FilmsService

    lateinit var filmDao: FilmsDao

    override fun onCreate() {
        super.onCreate()
        initRetrofit()
           initRoom()
    }

    private fun initRetrofit() {

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(ChuckerInterceptor(context = applicationContext)).build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://your.api.url/v2/")
            .addConverterFactory(GsonConverterFactory.create())

            .build()

        apiService = retrofit.create(FilmsService::class.java)
    }
    private fun initRoom() {
        val room = Room.databaseBuilder(applicationContext, FilmDataBase::class.java, "film-db").build()
        filmDao = room.filmRemoteDao()
    }
}




