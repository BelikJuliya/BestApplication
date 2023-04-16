package com.example.data.db

import androidx.room.Database
import com.example.data.remote.model.FilmRemoteModel

@Database(entities = [FilmRemoteModel::class], version = 1)
abstract class FilmDataBase  {
    abstract fun filmRemoteDao(): FilmsDao
}
