package com.example.domain.usecase

import com.example.domain.IFilmRepository

class DownloadImageUseCase(private val repository: IFilmRepository) {

    suspend fun downloadImage(url: String) {

    }
}