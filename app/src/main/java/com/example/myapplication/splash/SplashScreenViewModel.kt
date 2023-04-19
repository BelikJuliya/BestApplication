import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.LoadFilmsFromRemoteUseCase
import com.example.domain.usecase.SaveFilmsToDbUseCase
import com.example.myapplication.base.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class SplashScreenViewModel(
    private val loadFilmsFromRemoteUseCase: LoadFilmsFromRemoteUseCase,
    private val saveFilmsToDbUseCase: SaveFilmsToDbUseCase
) : ViewModel() {
    fun fetchFilms(apiKey: String) = flow {
        val films = loadFilmsFromRemoteUseCase.loadFilms(apiKey = apiKey)
        emit(saveFilmsToDbUseCase.saveFilmsToDb(films))
    }.flowOn(Dispatchers.IO)
}
