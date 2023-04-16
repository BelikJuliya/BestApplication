package com.example.domain

import com.example.domain.model.FilmDomainModel

interface IRemoteDataSource {

    suspend fun loadFilms(apiKey: String): List<FilmDomainModel>

}