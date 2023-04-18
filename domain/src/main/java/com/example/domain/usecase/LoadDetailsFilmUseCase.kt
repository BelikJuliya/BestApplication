package com.example.domain.usecase

import com.example.domain.IRepository
import com.example.domain.model.FilmDetailsScreenDomainModel

class LoadDetailsFilmUseCase(
    private val repository: IRepository
) {
    suspend fun loadFilmDetails(apiKey: String, id: String) : FilmDetailsScreenDomainModel {
       return repository.loadDetailsFilm(apiKey,id)

    }
}