package com.example.domain

import com.example.domain.model.FilmDetailsDomainModel
import com.example.domain.model.FilmDomainModel

interface IRemoteDataSource {

    suspend fun loadFilms(apiKey: String): List<FilmDomainModel>

    suspend fun filmDetails(id: String, apiKey: String): FilmDetailsDomainModel

}