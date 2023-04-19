package com.example.myapplication.details

import com.example.domain.model.FilmDetailsDomainModel

sealed class FilmDetailState {
    open class Success(val data: FilmDetailsDomainModel) : FilmDetailState()
    class Error(val message: String = "") : FilmDetailState()
    object Loading : FilmDetailState()
}