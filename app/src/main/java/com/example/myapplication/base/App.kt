package com.example.myapplication.base

import android.app.Application
import android.arch.persistence.room.Room
import com.example.data.remote.FilmsService
import com.example.data.remote.filmDb.FilmDataBase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://imdb-api.com/"
class App : Application() {

    lateinit var apiService: FilmsService

    override fun onCreate() {
        super.onCreate()
        initRetrofit()
        initDb()
    }

    private fun initRetrofit() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://your.api.url/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(FilmsService::class.java)
    }
    private fun initDb() {
        val db = Room.databaseBuilder(applicationContext,FilmDataBase :: class.java,"film-db")
    }
}