package com.example.myapplication.films_list_flow

import com.example.domain.model.FilmDomainModel

sealed class FilmsListUiState {
    object Idle: FilmsListUiState()
    object Empty : FilmsListUiState()
    object Loading : FilmsListUiState()
    class Success(val data: List<FilmDomainModel>) : FilmsListUiState()
    class Error(val message: String) : FilmsListUiState()
}