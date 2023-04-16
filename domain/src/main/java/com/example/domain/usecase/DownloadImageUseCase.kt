package com.example.domain.usecase

import com.example.domain.IRemoteDataSource

class DownloadImageUseCase(private val repository: IRemoteDataSource) {

    suspend fun downloadImage(url: String) {

    }
}