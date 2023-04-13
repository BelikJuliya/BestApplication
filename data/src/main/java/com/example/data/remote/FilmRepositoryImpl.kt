package com.example.data.remote

import com.example.domain.IFilmRepository
import com.example.domain.model.FilmDomainModel

const val ENGLISH = "en"

class FilmRepositoryImpl(
    private val filmsService: FilmsService,
) : IFilmRepository {

    override suspend fun loadFilms(apiKey: String): List<FilmDomainModel> {
        return filmsService.getLatestRates(apiKey = apiKey, lang = ENGLISH).toDomainObject()
    }
}