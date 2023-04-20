package com.example.myapplication.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.FilmDomainModel
import com.example.domain.usecase.DetailsFilmUseCase
import com.example.domain.usecase.RemoveFilmFromFavouriteUseCase
import com.example.domain.usecase.SaveToFavouriteUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val loafDetailsFilmUseCase: DetailsFilmUseCase,
    private val saveToFavourite: SaveToFavouriteUseCase,
    private val removeFromFavourite: RemoveFilmFromFavouriteUseCase
) : ViewModel() {

    private val _detailsUiState = MutableStateFlow<FilmDetailState>(FilmDetailState.Loading)
    val detailsUiState: StateFlow<FilmDetailState> = _detailsUiState

    fun fetchFilmDetails(apiKey: String, id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val filmsDetails = loafDetailsFilmUseCase.loadFilmDetails(apiKey = apiKey, id = id)
                if (!filmsDetails.errorMessage.isNullOrEmpty()) {
                    _detailsUiState.value = FilmDetailState.Error()
                } else {
                    _detailsUiState.value = FilmDetailState.Success(filmsDetails)
                }
            } catch (ex: Exception) {
                _detailsUiState.value = FilmDetailState.Error()
            }
        }
    }

    fun saveToFavourite(film: FilmDomainModel) {
        viewModelScope.launch(Dispatchers.IO) {
            saveToFavourite.saveToFavourite(film)
        }
    }

    fun deleteFromFavourite(film: FilmDomainModel) {
        viewModelScope.launch(Dispatchers.IO) {
            removeFromFavourite.remove(film)
        }
    }

    fun goToYouTube(id: String?) {

    }
}
