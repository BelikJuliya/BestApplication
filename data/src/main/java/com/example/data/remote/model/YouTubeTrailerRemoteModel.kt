package com.example.data.remote.model

import com.example.data.remote.IResponse
import com.example.domain.model.YouTubeTrailerDomainModel

data class YouTubeTrailerRemoteModel(
    val errorMessage: String,
    val fullTitle: String,
    val imDbId: String,
    val title: String,
    val type: String,
    val videoId: String,
    val videoUrl: String,
    val year: String
):IResponse<YouTubeTrailerDomainModel> {
    override fun toDomainObject(): YouTubeTrailerDomainModel {
        return YouTubeTrailerDomainModel(videoUrl = videoUrl)
    }
}
