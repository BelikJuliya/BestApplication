package com.example.domain

import com.example.domain.model.FilmDomainModel

interface IFilmRepository {

    suspend fun loadFilms(apiKey: String): List<FilmDomainModel>

//    suspend fun saveCurrency(currencyDomainModel: CurrencyDomainModel)
//
//    suspend fun deleteCurrency(currencyDomainModel: CurrencyDomainModel)
}