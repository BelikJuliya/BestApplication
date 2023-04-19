package com.example.myapplication.details

import com.example.domain.model.FilmDetailsDomainModel

sealed class FilmDetailState {
    open class Success(val data: FilmDetailsDomainModel) : FilmDetailState()
    object Error : FilmDetailState()
    object Loading : FilmDetailState()
}