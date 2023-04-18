package com.example.domain.usecase

import com.example.domain.IRepository
import com.example.domain.model.FilmDomainModel

class GetFilmsFromDbUseCase(
    private val repository: IRepository
) {

    suspend fun fetchFilmsList(): List<FilmDomainModel> {
        return repository.fetchFilmsFromDb()
    }

    suspend fun fetchFavouriteFilms(): List<FilmDomainModel> {
        return repository.fetchFavouriteFilms()
    }
}