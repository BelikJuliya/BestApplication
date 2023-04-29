package com.example.domain

import com.example.domain.model.FilmDetailsDomainModel
import com.example.domain.model.FilmDomainModel

interface IRepository {

    suspend fun saveFilms(films: List<FilmDomainModel>)

    suspend fun loadFilms(apiKey: String): List<FilmDomainModel>

    suspend fun fetchFilmsFromDb(): List<FilmDomainModel>

    suspend fun fetchFavouriteFilms(): List<FilmDomainModel>

    suspend fun saveFilmToFavourite(filmDomainModel: FilmDomainModel)

    suspend fun removeFromFavourite(filmDomainModel: FilmDomainModel)

    suspend fun filmDetails(id: String, apiKey: String): FilmDetailsDomainModel

    suspend fun clearAll(favouriteFilmsList: List<FilmDomainModel>)

    suspend fun updateSaveState(id: String, isSaved: Boolean)

    suspend fun saveFilmById(id: String)

    suspend fun removeFilmById(id: String)

}