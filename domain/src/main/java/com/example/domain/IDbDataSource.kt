package com.example.domain

import com.example.domain.model.FilmDomainModel

interface IDbDataSource {

    suspend fun saveFilmsToDb(films: List<FilmDomainModel>)
    suspend fun fetchFilmsFromDb(): List<FilmDomainModel>
    suspend fun saveToFavourite(filmDomainModel: FilmDomainModel)
    suspend fun fetchFavouriteFilms(): List<FilmDomainModel>
}