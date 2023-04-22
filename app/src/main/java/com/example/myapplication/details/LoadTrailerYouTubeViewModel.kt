package com.example.myapplication.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.YouTubeTrailerDomainModel
import com.example.domain.usecase.LoadYouTubeTrailerUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
class LoadTrailerYouTubeViewModel(
    private val loadYouTubeTrailerUseCase: LoadYouTubeTrailerUseCase
) : ViewModel() {
    private val _trailerUiState = MutableStateFlow<TrailerState>(TrailerState.Loading)
    val trailerUiState: StateFlow<TrailerState> = _trailerUiState

    fun loadTrailer(apiKey: String, id: String): Flow<YouTubeTrailerDomainModel> {
        return flow {
            try {
                val result = loadYouTubeTrailerUseCase.getYotTubeTrailer(apiKey = apiKey, id = id)
                emit(result)
                _trailerUiState.value = TrailerState.Success(result)
            } catch (e: Exception) {
                _trailerUiState.value = TrailerState.Error
            }
        }.flowOn(Dispatchers.IO)
    }
}