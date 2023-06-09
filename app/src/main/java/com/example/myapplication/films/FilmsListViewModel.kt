package com.example.myapplication.films

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.FilmDomainModel
import com.example.domain.usecase.GetAllFilmsFromDbUseCase
import com.example.domain.usecase.RemoveFilmFromFavouriteUseCase
import com.example.domain.usecase.SaveToFavouriteUseCase
import com.example.domain.usecase.UpdateSaveStateUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FilmsListViewModel(
    private val saveToFavouriteUseCase: SaveToFavouriteUseCase,
    private val deleteFilmUseCase: RemoveFilmFromFavouriteUseCase,
    private val getAllFilmsFromDbUseCase: GetAllFilmsFromDbUseCase,
    private val updateSaveStateUseCase: UpdateSaveStateUseCase
) : ViewModel() {

    private val TAG = this.javaClass.simpleName
    private var filmsList = mutableListOf<FilmDomainModel>()
    private val _filmsListUiState = MutableStateFlow<FilmsListUiState>(FilmsListUiState.Idle)
    val filmsListUiState: StateFlow<FilmsListUiState> = _filmsListUiState

    fun fetchFilms() {
        _filmsListUiState.value = FilmsListUiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                filmsList.addAll(getAllFilmsFromDbUseCase.fetchFilmsList())
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

    fun delete(film: FilmDomainModel) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                deleteFilmUseCase.remove(film)
                updateSaveStateUseCase.updateSaveState(film.id, false)
                mapSavedState(film, false)
                _filmsListUiState.value = FilmsListUiState.Success(data = filmsList)
            } catch (ex: Exception) {
                Log.e(TAG, "removeFromSaved: remove from saved failed", ex)
            }
        }
    }

    fun save(film: FilmDomainModel) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                saveToFavouriteUseCase.saveToFavourite(film)
                updateSaveStateUseCase.updateSaveState(film.id, true)
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

    fun findIsSavedFilmById(id: String): Boolean {
        return filmsList.find { it.id == id }?.isSaved ?: false
    }

    fun sort(order: Order){
        when(order){
            is Order.Alphabet -> filmsList.sortBy { it.title }
            is Order.Rating -> filmsList.sortBy { it.rating }
            is Order.Popularity -> filmsList.sortBy { it.rank }
            is Order.Count -> filmsList.sortBy { it.iMDbRatingCount }
        }
        _filmsListUiState.value = FilmsListUiState.Success(data = filmsList)
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