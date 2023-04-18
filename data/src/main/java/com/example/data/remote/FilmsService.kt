package com.example.data.remote

import com.example.data.remote.model.FilmsListRemoteModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FilmsService {

    @GET("API/MostPopularMovies/")
    suspend fun loadFilms(
        @Path("lang") lang: String?,
        @Path("apikey") apiKey: String
    ): FilmsListRemoteModel
}