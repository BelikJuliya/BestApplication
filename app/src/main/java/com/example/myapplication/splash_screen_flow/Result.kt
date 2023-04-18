package splash_screen_flow

import com.example.domain.model.FilmDomainModel

sealed class Result {
    open class Success() : Result()
    object Error : Result()
    object Loading : Result()
}