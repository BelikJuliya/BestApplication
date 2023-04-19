package com.example.myapplication.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.FilmDetailsDomainModel
import com.example.domain.usecase.LoadDetailsFilmUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FragmentDetailsViewModel(
    private val loafDetailsFilmUseCase: LoadDetailsFilmUseCase
) : ViewModel() {

    private val _detailsUiState = MutableStateFlow<FilmDetailState>(FilmDetailState.Loading)
    val detailsUiState: StateFlow<FilmDetailState> = _detailsUiState

    fun fetchFilmDetails(apiKey: String, id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val filmsDetails = loafDetailsFilmUseCase.loadFilmDetails(apiKey = apiKey, id = id)
                _detailsUiState.value = FilmDetailState.Success(filmsDetails)
            } catch (ex: Exception) {
                _detailsUiState.value = FilmDetailState.Error
            }
        }
    }

    fun goToYouTube(id: Int){

    }
}
