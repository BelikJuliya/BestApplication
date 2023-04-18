package com.example.data.remote.model

import com.example.data.remote.IResponse
import com.example.domain.model.FilmDetailsScreenDomainModel

data class FilmsDetailsRemoteModel(
    val id : Int,
    val image: String,
    val title: String,
    val plot: String,
    val imDbRating: RatingRemoteModel,
    val releaseDate: String,
    val actorList: List<ActorRemoteModel>
    // Кнопка для трейлера на ютуб
) : IResponse<FilmDetailsScreenDomainModel> {
    override fun toDomainObject(): FilmDetailsScreenDomainModel {
     return   FilmDetailsScreenDomainModel(
            id =id,
            image = image,
            title = title,
            plot = plot,
            imDbRating = imDbRating.toDomainObject(),
            releaseDate = releaseDate,
            actorList = actorList.map { it.toDomainObject() }
        )
    }
}