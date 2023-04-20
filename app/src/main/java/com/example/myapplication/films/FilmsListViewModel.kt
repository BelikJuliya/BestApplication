package com.example.myapplication.films

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.FilmDomainModel
import com.example.domain.usecase.GetFilmsFromDbUseCase
import com.example.domain.usecase.SaveFilmsToDbUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class FilmsListViewModel(
    private val saveFilmsToDbUseCase: SaveFilmsToDbUseCase,
    private val getFilmsFromDbUseCase: GetFilmsFromDbUseCase
) : ViewModel() {

    private val TAG = this.javaClass.simpleName
    private var filmsList = mutableListOf<FilmDomainModel>()
    private val _filmsListUiState = MutableStateFlow<FilmsListUiState>(FilmsListUiState.Idle)
    val filmsListUiState: StateFlow<FilmsListUiState> = _filmsListUiState

    init {
        fetchFilms()
    }

    private fun fetchFilms() {
        _filmsListUiState.value = FilmsListUiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                filmsList.addAll(getFilmsFromDbUseCase.fetchFilmsList())
                if (filmsList.isEmpty()) {
                    _filmsListUiState.value = FilmsListUiState.Empty
                } else {
                    _filmsListUiState.value = FilmsListUiState.Success(data = filmsList)
                }
            } catch (ex: Exception) {
                _filmsListUiState.value = FilmsListUiState.Error(ex.message ?: "")
            }
        }
    }

    fun removeFromSaved(film: FilmDomainModel) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                saveFilmsToDbUseCase.removeFromFavourite(film)
                mapSavedState(film, false)
                _filmsListUiState.value = FilmsListUiState.Success(data = filmsList)
            } catch (ex: Exception) {
                Log.e(TAG, "removeFromSaved: remove from saved failed", ex)
            }
        }
    }

    fun saveFilm(film: FilmDomainModel) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                saveFilmsToDbUseCase.saveToFavourite(film)
                mapSavedState(film, true)
                _filmsListUiState.value = FilmsListUiState.Success(data = filmsList)
            } catch (ex: Exception) {
                Log.e(TAG, "saving to favourite: remove from saved failed", ex)
            }
        }
    }

    private fun mapSavedState(film: FilmDomainModel, isSaved: Boolean) {
        val newList = filmsList.map {
            if (it.id == film.id) {
                it.copy(isSaved = isSaved)
            } else it
        }.toMutableList()
        filmsList = newList
    }
}

//    private suspend fun mapImages(film: FilmDomainModel) {
//        film.imageUrl?.let { imageUrl ->
//            downLoadImageFlow(imageUrl).collect {
//                filmsList.map {
//                    if (it.id == film.id) {
//                        it.copy(imageFile = bitmap)
//                    } else it
//                }
//            }
//        }
//    }

//    fun loadBitmap(urlString: String) {
//        val def = viewModelScope.async { downLoadImageFlow(urlString) }
//        def.onAwait
//    }



//    fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
//        val bytes = ByteArrayOutputStream()
//        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
//        val path: String = Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null)
//        return Uri.parse(path)
//    }
//
//    fun getRealPathFromURI(uri: Uri?): String? {
//        val cursor: Cursor = getContentResolver().query(uri, null, null, null, null)
//        cursor.moveToFirst()
//        val idx: Int = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
//        return cursor.getString(idx)
//    }

//    fun downLoadImage(imageUrl: String, onImageLoaded: (Bitmap) -> Unit) {
//        viewModelScope.launch(Dispatchers.IO) {
//            try {
//                val url = URL(imageUrl)
//                val connection = url.openConnection() as HttpURLConnection
//                connection.doInput = true
//                connection.connect()
//                val input: InputStream = connection.inputStream
//                val bitmap = BitmapFactory.decodeStream(input)
//                onImageLoaded(bitmap)
//            } catch (e: IOException) {
//                e.printStackTrace()
//            }
//        }
//    }

//}
//        var bitmap: Bitmap? = null
//        viewModelScope.launch(Dispatchers.IO) {
//            var urlConnection: HttpURLConnection? = null
//            var inputStream: InputStream? = null
//            try {
//                urlConnection = URL(url).openConnection() as HttpURLConnection
//                inputStream = BufferedInputStream(urlConnection.inputStream)
//                val bitmapDef = async { BitmapFactory.decodeStream(inputStream) }
//                bitmap = bitmapDef.await()
//                return@launch
//            } catch (e: IOException) {
//                e.printStackTrace()
//            } finally {
//                urlConnection?.disconnect()
//                inputStream?.close()
//            }
//        }
//        return bitmap
//    }
//}