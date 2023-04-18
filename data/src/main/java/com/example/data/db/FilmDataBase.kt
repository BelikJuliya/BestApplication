package com.example.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.remote.model.FilmRemoteModel

@Database(entities = [FilmEntity::class], version = 1)
abstract class FilmDataBase: RoomDatabase() {
    abstract fun filmRemoteDao(): FilmsDao
}
