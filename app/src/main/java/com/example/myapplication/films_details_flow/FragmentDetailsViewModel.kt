package com.example.myapplication.films_details_flow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.LoadDetailsFilmUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FragmentDetailsViewModel(
    private val loafDetailsFilmUseCase : LoadDetailsFilmUseCase
) : ViewModel() {

    private val _splashUiState = MutableStateFlow<Result>(Result.Loading)
    val filmsListUiState: StateFlow<Result> = _splashUiState

    fun fetchFilms(apiKey: String, id : String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val filmsDetails = loafDetailsFilmUseCase.loadFilmDetails(apiKey = apiKey, id = id)
                _splashUiState.value = Result.Success()
            } catch (ex: Exception) {
                _splashUiState.value = Result.Error
            }
        }
    }
}
