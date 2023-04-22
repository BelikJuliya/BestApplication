package com.example.domain.usecase

import com.example.domain.IRepository
import com.example.domain.model.FilmDomainModel

class RemoveFilmFromFavouriteUseCase(
    val repository: IRepository
) {
    suspend fun remove(filmDomainModel: FilmDomainModel) {
         repository.removeFromFavourite(filmDomainModel)
    }
}