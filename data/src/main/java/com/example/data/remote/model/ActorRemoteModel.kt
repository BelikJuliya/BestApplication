package com.example.data.remote.model

import com.example.data.remote.IResponse
import com.example.domain.model.ActorDomainModel

data class ActorRemoteModel(
    val asCharacter: String,
    val id: String,
    val image: String,
    val name: String
) : IResponse<ActorDomainModel> {
    override fun toDomainObject(): ActorDomainModel {
        return ActorDomainModel(
            asCharacter = asCharacter,
            id = id,
            image = image,
            name = name
        )
    }
}