package com.example.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.db.entity.FavouriteFilmEntity
import com.example.data.db.entity.FilmEntity

@Dao
interface
FilmsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFilmsList(films: List<FilmEntity>)

    @Query("SELECT * FROM films")
    suspend fun getFilms(): List<FilmEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveToFavourite(favouriteEntity: FavouriteFilmEntity)

    @Query("SELECT * FROM favourite")
    suspend fun fetchFavouriteFilmsList(): List<FilmEntity>

    @Delete
    suspend fun removeFilmFromFavourite(filmEntity: FavouriteFilmEntity)

    @Delete
    suspend fun clearAll(favouriteFilmsList: List<FavouriteFilmEntity>)

}

