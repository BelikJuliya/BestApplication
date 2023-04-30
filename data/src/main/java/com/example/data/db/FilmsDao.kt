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

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveFilmsList(films: List<FilmEntity>)

    @Query("SELECT * FROM films")
    suspend fun getFilms(): List<FilmEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveToFavourite(favouriteEntity: FavouriteFilmEntity)

    @Query("SELECT * FROM favourite")
    suspend fun fetchFavouriteFilmsList(): List<FilmEntity>

    @Delete
    suspend fun removeFilmFromFavourite(filmEntity: FavouriteFilmEntity)

    @Query("DELETE FROM favourite")
    suspend fun clearAll()

    @Query("UPDATE films SET isSaved = 0 WHERE isSaved= 1")
    suspend fun changeSaveState()

    @Query("SELECT * from films WHERE id= :id")
    fun getFilmById(id: String): FilmEntity

    @Query("UPDATE films SET isSaved = :isSaved WHERE id=:id")
    suspend fun updateSaveState(id: String, isSaved: Boolean)

}

