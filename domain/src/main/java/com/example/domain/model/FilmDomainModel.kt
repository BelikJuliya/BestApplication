package com.example.domain.model

import com.example.domain.BaseModel
import com.example.domain.BaseModelPayload
import java.io.File
import java.nio.file.Path

data class FilmDomainModel(
    val id: String,
    val title: String?,
    val imageUrl: String?,
    val rating: String?,
    var isSaved: Boolean = false,
    val bitmapPath: Path? = null,
    val rank: String?,
    val iMDbRatingCount: String?
) : BaseModel {

    override fun isIdDiff(other: BaseModel): Boolean {
        return if (other !is FilmDomainModel) false
        else this.id == other.id
    }

    override fun isContentDiff(other: BaseModel): Boolean {
        if (other !is FilmDomainModel) return false
        if (other.id != this.id) return false
        if (other.title != this.title) return false
        if (other.imageUrl != this.imageUrl) return false
        if (other.rating != this.rating) return false
        if (other.isSaved != this.isSaved) return false
        return true
    }

    override fun getPayloadDiff(other: BaseModel): MutableList<Any> {
        val payloads = mutableListOf<Any>()
        if (other !is FilmDomainModel)
            return payloads
        if (other.title != this.title)
            payloads.add(BaseModelPayload.TITLE)
        if (other.imageUrl != this.imageUrl)
            payloads.add(BaseModelPayload.IMAGE)
        if (other.rating != this.rating)
            payloads.add(BaseModelPayload.RATING)
        if (other.isSaved != this.isSaved)
            payloads.add(BaseModelPayload.SAVED_STATE)
        return payloads
    }
}
