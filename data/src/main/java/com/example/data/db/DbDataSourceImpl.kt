package com.example.data.db

import com.example.domain.IDbDataSource
import com.example.domain.model.FilmDomainModel

class DbDataSourceImpl(
    private val dao: FilmsDao
) : IDbDataSource {

    override suspend fun saveFilmsToDb(films: List<FilmDomainModel>) {
        val result = dao.saveFilmsList(films.map { FilmEntity.fromDomainObject(it) })
    }

    override suspend fun fetchFilmsFromDb(): List<FilmDomainModel> {
        return dao.getFilms().map { it.toDomainObject() }
    }

    override suspend fun saveToFavourite(filmDomainModel: FilmDomainModel) {
        dao.saveToFavourite(FilmEntity.fromDomainObject(filmDomainModel))
    }
}