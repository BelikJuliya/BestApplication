package com.example.domain.usecase

import com.example.domain.IRemoteDataSource
import com.example.domain.model.FilmDomainModel

class LoadFilmsFromRemoteUseCase(
    private val repository: IRemoteDataSource
) {

    suspend fun loadFilms(apiKey: String): List<FilmDomainModel> {
        return repository.loadFilms(apiKey)
    }
}