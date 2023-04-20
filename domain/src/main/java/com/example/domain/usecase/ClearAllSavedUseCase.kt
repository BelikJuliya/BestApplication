package com.example.domain.usecase

import com.example.domain.IRepository
import com.example.domain.model.FilmDomainModel

class ClearAllSavedUseCase(
    val repository: IRepository
) {

    suspend fun clearAll(list: List<FilmDomainModel>) {
        repository.clearAll(list)
    }
}