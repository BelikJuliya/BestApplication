package com.example.data.remote.filmDb

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "films")
class FilmRemoteModelDb(
    @PrimaryKey val id: String,
    val rank: String,
    val rankUpDown: String,
    val title: String,
    val fullTitle: String,
    val year: String,
    val image: String,
    val crew: String,
    val iMDbRating: String,
    val iMDbRatingCount: String)
