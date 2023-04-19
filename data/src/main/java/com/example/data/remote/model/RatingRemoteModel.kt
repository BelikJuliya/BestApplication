package com.example.data.remote.model

import com.example.data.remote.IResponse
import com.example.domain.model.RatingDomainModel

data class RatingRemoteModel(
    val imDb: String?,
) : IResponse<RatingDomainModel> {
    override fun toDomainObject(): RatingDomainModel {
        return RatingDomainModel(imDb = imDb)
    }
}