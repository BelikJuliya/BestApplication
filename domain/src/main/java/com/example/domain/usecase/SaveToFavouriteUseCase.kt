package com.example.domain.usecase

import com.example.domain.IRepository
import com.example.domain.model.FilmDomainModel

class SaveToFavouriteUseCase(
    private val repository: IRepository
) {
    suspend fun saveToFavourite(film: FilmDomainModel) {
        repository.saveFilmToFavourite(film)
    }

    suspend fun saveFilmById(id: String) {

    }
}