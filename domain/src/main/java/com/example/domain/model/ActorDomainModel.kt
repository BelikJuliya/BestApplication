package com.example.domain.model

import com.example.domain.BaseModel

data class ActorDomainModel(
    val asCharacter: String,
    val id: String,
    val image: String,
    val name: String
):BaseModel{
    override fun isIdDiff(other: BaseModel): Boolean {
        return super.isIdDiff(other)
    }

    override fun isContentDiff(other: BaseModel): Boolean {
        return super.isContentDiff(other)
    }

    override fun getPayloadDiff(other: BaseModel): MutableList<Any> {
        return super.getPayloadDiff(other)
    }
}