package com.example.data.remote.model

import com.example.data.remote.IResponse
import com.example.domain.model.ActorDomainModel

data class ListActorRemoteModel(
    val items: List<ActorRemoteModel>

) : IResponse<List<ActorDomainModel>> {
    override fun toDomainObject(): List<ActorDomainModel> {
        return items.map {
            ActorDomainModel(
                asCharacter = it.asCharacter,
                id = it.id,
                image = it.image,
                name = it.name
            )
        }
    }
}
