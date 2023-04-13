package com.example.domain.usecase

import com.example.domain.IFilmRepository
import com.example.domain.model.FilmDomainModel

class LoadFilmsUseCase(
    private val repository: IFilmRepository
) {

    suspend fun loadFilms(apiKey: String): List<FilmDomainModel> {
        return repository.loadFilms(apiKey)
    }
}