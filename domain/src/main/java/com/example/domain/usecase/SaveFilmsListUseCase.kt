package com.example.domain.usecase

import com.example.domain.IRepository
import com.example.domain.model.FilmDomainModel

class SaveFilmsListUseCase(
    val repository: IRepository
) {
    suspend fun saveAll(filmsList: List<FilmDomainModel>){
        repository.saveFilms(filmsList)
    }
}