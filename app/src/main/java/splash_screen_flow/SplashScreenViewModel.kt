package splash_screen_flow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.LoadFilmsFromRemoteUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SplashScreenViewModel(
    private val loadFilmsFromRemoteUseCase: LoadFilmsFromRemoteUseCase
) : ViewModel() {

    private val _splashUiState = MutableStateFlow<Result>(Result.Loading)
    val filmsListUiState: StateFlow<Result> = _splashUiState

    fun fetchFilms(apiKey: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val films = loadFilmsFromRemoteUseCase.loadFilms(apiKey = apiKey)
                _splashUiState.value = Result.Success()
            } catch (ex: Exception) {
                _splashUiState.value = Result.Error
            }
        }
    }
}

