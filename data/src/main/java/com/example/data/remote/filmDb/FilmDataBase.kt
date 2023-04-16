package com.example.data.remote.filmDb

import android.arch.persistence.room.Database
import com.example.data.remote.model.FilmRemoteModel

@Database(entities = [FilmRemoteModel::class], version = 1)
abstract class FilmDataBase  {
    abstract fun filmRemoteDao(): FilmRemoteDao
}
