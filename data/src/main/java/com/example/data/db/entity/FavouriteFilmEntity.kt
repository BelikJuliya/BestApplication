package com.example.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.db.IEntity
import com.example.domain.model.FilmDomainModel

@Entity(tableName = "favourite")
class FavouriteFilmEntity(
    @PrimaryKey val id: String,
    val title: String,
    val image: String,
    val rating: String,
    val isSaved: Boolean,
    val rank: String?,
    val iMDbRatingCount: String?
) : IEntity<FilmDomainModel, FilmEntity> {

    override fun toDomainObject(): FilmDomainModel {
        return FilmDomainModel(
            id = this.id,
            title = this.title,
            imageUrl = this.image,
            rating = this.rating,
            isSaved = this.isSaved,
            rank = this.rank,
            iMDbRatingCount = this.iMDbRatingCount
        )
    }

    companion object {

        fun fromDomainObject(model: FilmDomainModel): FavouriteFilmEntity {
            return FavouriteFilmEntity(
                id = model.id,
                title = model.title ?: "",
                image = model.imageUrl ?: "",
                rating = model.rating ?: "",
                isSaved = model.isSaved,
                rank = model.rank,
                iMDbRatingCount = model.iMDbRatingCount
            )
        }
    }
}