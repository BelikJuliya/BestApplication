package com.example.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface
FilmsDao {

    @Insert
    suspend fun saveFilmsList(films: List<FilmEntity>)

    @Query("SELECT * FROM films")
    suspend fun getFilms(): List<FilmEntity>

    @Insert
    suspend fun saveToFavourite(filmEntity: FilmEntity)

}

