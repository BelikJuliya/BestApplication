package splash_screen_flow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.LoadFilmsFromRemoteUseCase
import com.example.myapplication.films_list_flow.FilmsListUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SplashScreenViewModel(
    private val loadFilmsFromRemoteUseCase: LoadFilmsFromRemoteUseCase
) : ViewModel() {

    private val _filmsListUiState = MutableStateFlow<FilmsListUiState>(FilmsListUiState.Empty)
    val filmsListUiState: StateFlow<FilmsListUiState> = _filmsListUiState

    fun fetchFilms(apiKey: String) {
        _filmsListUiState.value = FilmsListUiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val films = loadFilmsFromRemoteUseCase.loadFilms(apiKey = apiKey)
                _filmsListUiState.value = FilmsListUiState.Success(data = films)
            } catch (ex: Exception) {
                _filmsListUiState.value = FilmsListUiState.Error(ex.message ?: "")
            }
        }
    }
}

