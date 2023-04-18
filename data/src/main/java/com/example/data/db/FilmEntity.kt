package com.example.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.model.FilmDomainModel

@Entity(tableName = "films")
class FilmEntity(
    @PrimaryKey val id: String,
    val title: String,
    val image: String,
    val rating: String,
    val isSaved: Boolean
) : IEntity<FilmDomainModel, FilmEntity> {

    override fun toDomainObject(): FilmDomainModel {
        return FilmDomainModel(
            id = this.id,
            title = this.title,
            imageUrl = this.image,
            rating = this.rating,
            isSaved = this.isSaved
        )
    }

    companion object {

        fun fromDomainObject(model: FilmDomainModel): FilmEntity {
            return FilmEntity(
                id = model.id,
                title = model.title,
                image = model.imageUrl,
                rating = model.rating,
                isSaved = model.isSaved
            )
        }
    }

//    override fun fromDomainObject(model: FilmDomainModel): FilmEntity {
//        return FilmEntity(
//            id = model.id,
//            title = model.title,
//            image = model.imageUrl,
//            rating = model.rating,
//            isSaved = model.isSaved
//        )
//    }
}