package com.example.domain.usecase

import com.example.domain.IRepository
import com.example.domain.model.FilmDomainModel

class GetFavouriteFilmsListUseCase(
    val repository: IRepository
) {

    suspend fun getFavoriteFilms(): List<FilmDomainModel> {
        return repository.fetchFavouriteFilms()
    }
}