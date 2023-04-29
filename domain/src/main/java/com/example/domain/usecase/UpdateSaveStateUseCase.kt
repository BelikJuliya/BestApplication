package com.example.domain.usecase

import com.example.domain.IRepository

class UpdateSaveStateUseCase(private val repository: IRepository) {

    suspend fun updateSaveState(id: String, isSaved: Boolean) {
        repository.updateSaveState(id, isSaved)
    }
}