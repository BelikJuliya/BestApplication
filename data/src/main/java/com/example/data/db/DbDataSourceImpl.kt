package com.example.data.db

import com.example.data.db.entity.FavouriteFilmEntity
import com.example.data.db.entity.FilmEntity
import com.example.domain.IDbDataSource
import com.example.domain.model.FilmDomainModel

class DbDataSourceImpl(
    private val dao: FilmsDao
) : IDbDataSource {

    override suspend fun saveFilmsToDb(films: List<FilmDomainModel>) {
        dao.saveFilmsList(films.map { FilmEntity.fromDomainObject(it) })
    }

    override suspend fun fetchFilmsFromDb(): List<FilmDomainModel> {
        return dao.getFilms().map { it.toDomainObject() }
    }

    override suspend fun saveToFavourite(filmDomainModel: FilmDomainModel) {
        dao.saveToFavourite(FavouriteFilmEntity.fromDomainObject(filmDomainModel))
    }

    override suspend fun fetchFavouriteFilms(): List<FilmDomainModel> {
        return dao.fetchFavouriteFilmsList().map { it.toDomainObject() }
    }

    override suspend fun removeFromFavourite(filmDomainModel: FilmDomainModel) {
        dao.removeFilmFromFavourite(FavouriteFilmEntity.fromDomainObject(filmDomainModel))
    }

    override suspend fun clearAll(favouriteFilmsList: List<FilmDomainModel>) {
        dao.clearAll(favouriteFilmsList.map { FavouriteFilmEntity.fromDomainObject(it) })
    }
}