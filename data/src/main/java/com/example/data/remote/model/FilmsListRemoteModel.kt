package com.example.data.remote.model

import android.example.remote.IResponse
import com.example.domain.model.FilmDomainModel

data class FilmsListRemoteModel(
    val items: List<FilmRemoteModel>,
    val errorMessage: String
) : IResponse<List<FilmDomainModel>> {

    override fun toDomainObject(): List<FilmDomainModel> {
        return items.map {
            FilmDomainModel(
                id = it.id,
                rank = it.rank,
                rankUpDown = it.rankUpDown,
                title = it.title,
                fullTitle = it.fullTitle,
                year = it.year,
                imageUrl = it.image,
                crew = it.crew,
                iMDbRating = it.iMDbRating,
                iMDbRatingCount = it.iMDbRatingCount,
            )
        }
    }
}
