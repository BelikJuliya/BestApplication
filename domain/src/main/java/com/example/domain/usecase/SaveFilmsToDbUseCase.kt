package com.example.domain.usecase

import com.example.domain.IRepository
import com.example.domain.model.FilmDomainModel

class SaveFilmsToDbUseCase(
    private val repository: IRepository
) {

    suspend fun saveFilmsToDb(films: List<FilmDomainModel>) {
        return repository.saveFilms(films)
    }

    suspend fun saveToFavourite(film: FilmDomainModel) {}

    suspend fun removeFromFavourite(film: FilmDomainModel) {}

    suspend fun fetchFilmsList(): List<FilmDomainModel> {
        return mutableListOf()
    }
}