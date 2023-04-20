package com.example.domain.usecase

import com.example.domain.IRepository
import com.example.domain.model.FilmDomainModel

class GetAllFilmsFromDbUseCase(
    private val repository: IRepository
) {
    suspend fun fetchFilmsList(): List<FilmDomainModel> {
        return repository.fetchFilmsFromDb()
    }
}