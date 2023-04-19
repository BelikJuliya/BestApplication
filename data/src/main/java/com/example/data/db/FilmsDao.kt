package com.example.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface
FilmsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFilmsList(films: List<FilmEntity>)

    @Query("SELECT * FROM films")
    suspend fun getFilms(): List<FilmEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveToFavourite(filmEntity: FilmEntity)

    @Query("SELECT * FROM films")
    suspend fun fetchFavouriteFilmsList(): List<FilmEntity>

}

