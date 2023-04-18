package com.example.domain.usecase

import com.example.domain.IRemoteDataSource
import com.example.domain.IRepository

class DownloadImageUseCase(private val repository: IRepository) {

    suspend fun downloadImage(url: String) {

    }
}