package com.example.data.remote.model

data class FilmRemoteModel(
    val id: String,
    val rank: String?,
    val rankUpDown: String?,
    val title: String?,
    val fullTitle: String?,
    val year: String?,
    val image: String?,
    val crew: String?,
    val iMDbRating: String?,
    val iMDbRatingCount: String?
)
