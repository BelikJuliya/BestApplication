package com.example.data.remote

import com.example.data.remote.model.FilmsDetailsRemoteModel
import com.example.data.remote.model.FilmsListRemoteModel
import retrofit2.http.GET
import retrofit2.http.Query

interface FilmsService {

    @GET("API/MostPopularMovies/")
    suspend fun loadFilms(
        @Query("lang") lang: String?,
        @Query("apikey") apiKey: String
    ): FilmsListRemoteModel

//https://imdb-api.com/%7Blang%7D/API/Title/%7BapiKey%7D/%7Bid%7D/%7Boptions?}

    @GET("API/Title/")
    suspend fun getFilmDetails(
        @Query("id") id: String,
        @Query("lang") lang: String?,
        @Query("options") options: String,
        @Query("apikey") apiKey: String
    ): FilmsDetailsRemoteModel
}