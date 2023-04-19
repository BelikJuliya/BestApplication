package com.example.data.remote

import com.example.domain.IRemoteDataSource
import com.example.domain.model.FilmDetailsDomainModel
import com.example.domain.model.FilmDomainModel

const val RUSSIAN = "ru"
const val RATINGS = "Ratings"

class RemoteDataSourceImpl(
    private val filmsService: FilmsService,
) : IRemoteDataSource {

    override suspend fun loadFilms(apiKey: String): List<FilmDomainModel> {
        return filmsService.loadFilms(apiKey = apiKey, lang = RUSSIAN).toDomainObject()
    }

    override suspend fun filmDetails(id: String, apiKey: String): FilmDetailsDomainModel {
        return filmsService.getFilmDetails(
            lang = RUSSIAN,
            id = id,
            apiKey = apiKey,
            options = RATINGS
        ).toDomainObject()
    }
}