package com.example.domain.usecase

import com.example.domain.IRepository
import com.example.domain.model.FilmDomainModel

class LoadFilmsFromRemoteUseCase(
    private val repository: IRepository
) {

    suspend fun loadFilms(apiKey: String): List<FilmDomainModel> {
        return repository.loadFilms(apiKey)
    }
}