

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.LoadFilmsFromRemoteUseCase
import com.example.domain.usecase.SaveFilmsToDbUseCase
import com.example.myapplication.base.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SplashScreenViewModel(
    private val loadFilmsFromRemoteUseCase: LoadFilmsFromRemoteUseCase,
    private val saveFilmsToDbUseCase: SaveFilmsToDbUseCase
) : ViewModel() {

    private val _splashUiState = MutableStateFlow<Result>(Result.Loading)
    val splashScreenUiState: StateFlow<Result> = _splashUiState

    fun fetchFilms(apiKey: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val films = loadFilmsFromRemoteUseCase.loadFilms(apiKey = apiKey)
                saveFilmsToDbUseCase.saveFilmsToDb(films)
                _splashUiState.value = Result.Success(data = Any())          //?
            } catch (ex: Exception) {
                _splashUiState.value = Result.Error
            }
        }
    }
}

