package com.example.data

import com.example.data.db.FilmEntity
import com.example.domain.IDbDataSource
import com.example.domain.IRemoteDataSource
import com.example.domain.IRepository
import com.example.domain.model.FilmDomainModel
import com.example.domain.model.FilmsDetailsDomainModel

class FilmRepositoryImpl(
    private val remoteDataSource: IRemoteDataSource,
    private val dbDataSource: IDbDataSource
) : IRepository {

    override suspend fun saveFilms(films: List<FilmDomainModel>) {
        dbDataSource.saveFilmsToDb(films)
    }

    override suspend fun loadFilms(apiKey: String): List<FilmDomainModel> {
        return remoteDataSource.loadFilms(apiKey)
    }

    override suspend fun fetchFilmsFromDb(): List<FilmDomainModel> {
        return dbDataSource.fetchFilmsFromDb()
    }

    override suspend fun filmDetails(): FilmsDetailsDomainModel {
        TODO("Not yet implemented")
    }

    override suspend fun fetchFavouriteFilms(): List<FilmDomainModel> {
        TODO("Not yet implemented")
    }

    override suspend fun saveFilmToFavourite(filmDomainModel: FilmDomainModel) {
        dbDataSource.saveToFavourite(filmDomainModel)
    }
}