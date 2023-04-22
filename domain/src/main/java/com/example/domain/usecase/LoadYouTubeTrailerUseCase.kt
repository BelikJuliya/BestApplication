package com.example.domain.usecase

import com.example.domain.IRepository
import com.example.domain.model.YouTubeTrailerDomainModel

class LoadYouTubeTrailerUseCase(
    private val repository:IRepository
){
    suspend fun getYotTubeTrailer(apiKey : String, id: String) : YouTubeTrailerDomainModel{
        return repository.getYouTubeTrailer(apiKey,id)
    }
}