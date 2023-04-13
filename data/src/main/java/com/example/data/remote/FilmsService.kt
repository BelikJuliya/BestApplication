package com.example.data.remote

import com.example.data.remote.model.FilmsListRemoteModel
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface FilmsService {

    @GET("/{lang}/API/MostPopularMovies/{apiKey}")
    suspend fun getLatestRates(
        @Path("lang") lang: String,
        @Path("apikey") apiKey: String
    ): FilmsListRemoteModel
}