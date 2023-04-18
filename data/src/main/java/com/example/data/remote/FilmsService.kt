package com.example.data.remote

import com.example.data.remote.model.FilmsListRemoteModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FilmsService {

    @GET("API/MostPopularMovies/")
    suspend fun loadFilms(
        @Query("lang") lang: String?,
        @Query("apikey") apiKey: String
    ): FilmsListRemoteModel
}