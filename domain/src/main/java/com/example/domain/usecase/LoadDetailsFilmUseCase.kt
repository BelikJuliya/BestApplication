package com.example.domain.usecase

import com.example.domain.IRepository
import com.example.domain.model.FilmDetailsDomainModel

class LoadDetailsFilmUseCase(
    private val repository: IRepository
) {
    suspend fun loadFilmDetails(apiKey: String, id: String) : FilmDetailsDomainModel {
       return repository.filmDetails(apiKey,id)

    }
}