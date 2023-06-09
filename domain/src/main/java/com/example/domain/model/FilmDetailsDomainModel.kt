package com.example.domain.model

import com.example.domain.BaseModel
import com.example.domain.BaseModelPayload

data class FilmDetailsDomainModel(
    val id: String?,
    val image: String?,
    val title: String?,
    val plot: String?,
    val imDbRating: String?,
    val releaseDate: String?,
    val actorList: List<ActorDomainModel>,
    val errorMessage: String?,
    var isSaved: Boolean = false
) : BaseModel {

    override fun isIdDiff(other: BaseModel): Boolean {
        return if (other !is FilmDetailsDomainModel) false
        else this.id == other.id
    }

    override fun isContentDiff(other: BaseModel): Boolean {
        if (other !is FilmDetailsDomainModel) return false
        if (other.id != this.id) return false
        if (other.title != this.title) return false
        if (other.image != this.image) return false
        if (other.plot != this.plot) return false
        if (other.imDbRating != this.imDbRating) return false
        if (other.releaseDate != this.releaseDate) return false
        if (other.actorList != this.actorList) return false
        return true
    }

    override fun getPayloadDiff(other: BaseModel): MutableList<Any> {
        val payloads = mutableListOf<Any>()
        if (other !is FilmDetailsDomainModel)
            return payloads
        if (other.title != this.title)
            payloads.add(BaseModelPayload.TITLE)
        if (other.image != this.image)
            payloads.add(BaseModelPayload.IMAGE)
        if (other.plot != this.plot)
            payloads.add(BaseModelPayload.PLOT)
        if (other.imDbRating != this.imDbRating)
            payloads.add(BaseModelPayload.IM_DB_RATING)
        if (other.releaseDate != this.releaseDate)
            payloads.add(BaseModelPayload.RELEASE_DATE)
        if (other.actorList != this.actorList)
            payloads.add(BaseModelPayload.ACTOR_LIST)
        return payloads
    }
}