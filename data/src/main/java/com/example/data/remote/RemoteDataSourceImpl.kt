package com.example.data.remote

import com.example.domain.IRemoteDataSource
import com.example.domain.model.FilmDomainModel

const val RUSSIAN = "ru"

class RemoteDataSourceImpl(
    private val filmsService: FilmsService,
) : IRemoteDataSource {

    override suspend fun loadFilms(apiKey: String): List<FilmDomainModel> {
        return filmsService.loadFilms(apiKey = apiKey, lang = RUSSIAN).toDomainObject()
    }
}