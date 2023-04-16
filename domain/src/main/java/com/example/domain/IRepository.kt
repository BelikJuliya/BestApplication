package com.example.domain

import com.example.domain.model.FilmDomainModel
import com.example.domain.model.FilmsDetailsDomainModel

interface IRepository {

    suspend fun saveFilms(films: List<FilmDomainModel>)

    suspend fun loadFilms(apiKey: String): List<FilmDomainModel>

    suspend fun fetchFilmsFromDb(): List<FilmDomainModel>

    suspend fun filmDetails(): FilmsDetailsDomainModel

    suspend fun fetchFavouriteFilms(): List<FilmDomainModel>
}