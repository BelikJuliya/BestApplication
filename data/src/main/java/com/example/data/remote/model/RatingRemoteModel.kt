package com.example.data.remote.model

import com.example.data.remote.IResponse
import com.example.domain.model.RatingDomainModel

data class RatingRemoteModel(
    val errorMessage: String,
    val filmAffinity: String,
    val fullTitle: String,
    val imDb: String,
    val imDbId: String,
    val metacritic: String,
    val rottenTomatoes: String,
    val theMovieDb: String,
    val title: String,
    val type: String,
    val year: String
) : IResponse<RatingDomainModel> {
    override fun toDomainObject(): RatingDomainModel {
        return RatingDomainModel(imDb = imDb)
    }
}