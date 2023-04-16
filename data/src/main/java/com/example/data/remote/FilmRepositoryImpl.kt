package com.example.data.remote

import com.example.domain.IFilmRepository
import com.example.domain.model.FilmDomainModel

const val RUSSIAN = "ru"

class FilmRepositoryImpl(
    private val filmsService: FilmsService,
) : IFilmRepository {

    override suspend fun loadFilms(apiKey: String): List<FilmDomainModel> {
        return filmsService.loadFilms(apiKey = apiKey, lang = RUSSIAN).toDomainObject()
    }
}