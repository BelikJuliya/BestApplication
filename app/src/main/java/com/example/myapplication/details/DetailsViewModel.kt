package com.example.myapplication.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.FilmDetailsDomainModel
import com.example.domain.model.FilmDomainModel
import com.example.domain.usecase.DetailsFilmUseCase
import com.example.domain.usecase.RemoveFilmFromFavouriteUseCase
import com.example.domain.usecase.SaveToFavouriteUseCase
import com.example.domain.usecase.UpdateSaveStateUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val loafDetailsFilmUseCase: DetailsFilmUseCase,
    private val saveToFavourite: SaveToFavouriteUseCase,
    private val removeFromFavourite: RemoveFilmFromFavouriteUseCase,
    private val updateSaveStateUseCase: UpdateSaveStateUseCase
) : ViewModel() {

    lateinit var filmId: String
    var isSaved:Boolean = false
    private lateinit var filmDetails: FilmDetailsDomainModel

    private val _detailsUiState = MutableStateFlow<FilmDetailState>(FilmDetailState.Loading)
    val detailsUiState: StateFlow<FilmDetailState> = _detailsUiState

    fun fetchFilmDetails(apiKey: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                filmDetails = loafDetailsFilmUseCase.loadFilmDetails(apiKey = apiKey, id = filmId)
                filmDetails.isSaved = isSaved
                if (!filmDetails.errorMessage.isNullOrEmpty()) {
                    _detailsUiState.value = FilmDetailState.Error()
                } else {
                    _detailsUiState.value = FilmDetailState.Success(filmDetails)
                }
            } catch (ex: Exception) {
                _detailsUiState.value = FilmDetailState.Error()
            }
        }
    }

    fun saveToFavourite() {
        viewModelScope.launch(Dispatchers.IO) {
            saveToFavourite.saveFilmById(filmId)
            updateSaveStateUseCase.updateSaveState(filmId, true)
            filmDetails.isSaved = true
            _detailsUiState.value = FilmDetailState.Success(filmDetails)
        }
    }

    fun deleteFromFavourite() {
        viewModelScope.launch(Dispatchers.IO) {
//            removeFromFavourite.remove(filmId)
//            updateSaveStateUseCase.updateSaveState(filmId.id, false)
        }
    }

    fun goToYouTube(id: String?) {

    }
}
