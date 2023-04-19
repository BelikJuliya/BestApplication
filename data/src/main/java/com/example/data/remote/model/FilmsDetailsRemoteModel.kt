package com.example.data.remote.model

import com.example.data.remote.IResponse
import com.example.domain.model.FilmDetailsDomainModel

data class FilmsDetailsRemoteModel(
    val id: String?,
    val image: String?,
    val title: String?,
    val plot: String?,
    val imDbRating: String?,
    val releaseDate: String?,
    val actorList: List<ActorRemoteModel>?,
    val errorMessage: String?
) : IResponse<FilmDetailsDomainModel> {

    override fun toDomainObject(): FilmDetailsDomainModel {
        return FilmDetailsDomainModel(
            id = id,
            image = image,
            title = title,
            plot = plot,
            imDbRating = imDbRating,
            releaseDate = releaseDate,
            actorList = actorList?.map { it.toDomainObject() } ?: mutableListOf(),
            errorMessage = errorMessage
        )
    }
}