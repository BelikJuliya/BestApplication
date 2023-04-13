package com.example.myapplication.films_list_flow

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.FilmDomainModel
import com.example.domain.usecase.LoadFilmsUseCase
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.net.UnknownHostException

class FilmsListViewModel(
    private val loadFilmsUseCase: LoadFilmsUseCase
) : ViewModel() {

    private val _filmsListUiState = MutableStateFlow<FilmsListUiState>(FilmsListUiState.Empty)
    val filmsListUiState: StateFlow<FilmsListUiState> = _filmsListUiState

    fun fetchFilms(apiKey: String) {
        _filmsListUiState.value = FilmsListUiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val films = loadFilmsUseCase.loadFilms(apiKey = apiKey)
                _filmsListUiState.value = FilmsListUiState.Success(data = films)
            } catch (ex: Exception) {
                _filmsListUiState.value = FilmsListUiState.Error(ex.message ?: "")
            }
        }
    }

    fun removeFromSaved(film: FilmDomainModel) {

    }

    fun saveFilm(film: FilmDomainModel) {

    }

    fun downLoadImage(url: String): Bitmap? {
        var bitmap: Bitmap? = null
        viewModelScope.launch(Dispatchers.IO) {
            var urlConnection: HttpURLConnection? = null
            var inputStream: InputStream? = null
            try {
                urlConnection = URL(url).openConnection() as HttpURLConnection
                inputStream = BufferedInputStream(urlConnection.inputStream)
                val bitmapDef = async { BitmapFactory.decodeStream(inputStream) }
                bitmap = bitmapDef.await()
                return@launch
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                urlConnection?.disconnect()
                inputStream?.close()
            }
        }
        return bitmap
    }
}