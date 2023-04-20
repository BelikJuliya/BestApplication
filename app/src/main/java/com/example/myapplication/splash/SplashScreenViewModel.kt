import androidx.lifecycle.ViewModel
import com.example.domain.usecase.LoadFilmsFromRemoteUseCase
import com.example.domain.usecase.SaveFilmsListUseCase
import com.example.domain.usecase.SaveToFavouriteUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class SplashScreenViewModel(
    private val loadFilmsFromRemoteUseCase: LoadFilmsFromRemoteUseCase,
    private val saveFilmsUseCase: SaveFilmsListUseCase
) : ViewModel() {
    fun fetchFilms(apiKey: String) = flow {
        val films = loadFilmsFromRemoteUseCase.loadFilms(apiKey = apiKey)
        emit(saveFilmsUseCase.saveAll(films))
    }.flowOn(Dispatchers.IO)
}
