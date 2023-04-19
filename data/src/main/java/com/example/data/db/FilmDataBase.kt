package com.example.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.db.entity.FavouriteFilmEntity
import com.example.data.db.entity.FilmEntity

@Database(entities = [FilmEntity::class, FavouriteFilmEntity::class], version = 1)
abstract class FilmDataBase : RoomDatabase() {

    abstract fun filmRemoteDao(): FilmsDao
}
