package com.example.data.remote.model

import com.example.data.remote.IResponse
import com.example.domain.model.FilmDomainModel

data class FilmsListRemoteModel(
    val items: List<FilmRemoteModel>,
    val errorMessage: String?
) : IResponse<List<FilmDomainModel>> {

    override fun toDomainObject(): List<FilmDomainModel> {
        return items.map {
            FilmDomainModel(
                id = it.id,
                title = it.title,
                imageUrl = it.image,
                rating = it.iMDbRating,
            )
        }
    }
}
