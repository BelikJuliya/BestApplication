package com.example.myapplication.favourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.FilmDomainModel
import com.example.domain.usecase.ClearAllSavedUseCase
import com.example.domain.usecase.GetAllFilmsFromDbUseCase
import com.example.domain.usecase.GetFavouriteFilmsListUseCase
import com.example.domain.usecase.RemoveFilmFromFavouriteUseCase
import com.example.domain.usecase.SaveToFavouriteUseCase
import com.example.myapplication.films.FilmsListUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavouriteViewModel(
    private val getFavouriteUseCase: GetFavouriteFilmsListUseCase,
    private val removeFilmsFromDbUseCase: RemoveFilmFromFavouriteUseCase,
    private val clearAllSavedFilmsUseCase: ClearAllSavedUseCase
) : ViewModel() {

    private val TAG = this.javaClass.simpleName

    private var favouriteList = mutableListOf<FilmDomainModel>()
    private val _favouriteUiState = MutableStateFlow<FilmsListUiState>(FilmsListUiState.Idle)
    val favouriteUiState: StateFlow<FilmsListUiState> = _favouriteUiState

    fun fetchFilms() {
        _favouriteUiState.value = FilmsListUiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                favouriteList.addAll(getFavouriteUseCase.getFavoriteFilms())
                if (favouriteList.isEmpty()) {
                    _favouriteUiState.value = FilmsListUiState.Empty
                } else {
                    _favouriteUiState.value = FilmsListUiState.Success(data = favouriteList)
                }
            } catch (ex: Exception) {
                _favouriteUiState.value = FilmsListUiState.Error(ex.message ?: "")
            }
        }
    }

    fun clearAll() {
        viewModelScope.launch { clearAllSavedFilmsUseCase.clearAll() }
        favouriteList.clear()
        _favouriteUiState.value = FilmsListUiState.Empty
    }
}