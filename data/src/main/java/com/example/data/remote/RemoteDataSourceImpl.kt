package com.example.data.remote

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.provider.MediaStore
import com.example.domain.IRemoteDataSource
import com.example.domain.model.FilmDetailsDomainModel
import com.example.domain.model.FilmDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

const val RUSSIAN = "ru"
const val RATINGS = "Ratings"

class RemoteDataSourceImpl(
    private val filmsService: FilmsService,
//    private val context: Context
) : IRemoteDataSource {

    override suspend fun loadFilms(apiKey: String): List<FilmDomainModel> {
        return filmsService.loadFilms(apiKey = apiKey, lang = RUSSIAN).toDomainObject()
    }

    override suspend fun filmDetails(id: String, apiKey: String): FilmDetailsDomainModel {
        return filmsService.getFilmDetails(
            lang = RUSSIAN,
            id = id,
            apiKey = apiKey,
            options = RATINGS
        ).toDomainObject()
    }

//    private fun downLoadImageFlow(urlString: String): Flow<Path?> = flow {
//
//        try {
//            val url = URL(urlString)
//            val connection = url.openConnection() as HttpURLConnection
//            connection.doInput = true
//            connection.connect()
//            val inputStream: InputStream = connection.inputStream
//            val bitmap = BitmapFactory.decodeStream(inputStream)
//            val file = File(Environment.getExternalStorageDirectory().absolutePath, "image.jpg")
//            val outputStream = FileOutputStream(file)
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
//            val path: String = MediaStore.Images.Media.insertImage(applicationContext.getContentResolver(), inImage, "Title", null)
//            return Uri.parse(path)
//            emit(bitmap.)
//
////            emit(bitmap)
//        } catch (e: IOException) {
//            e.printStackTrace()
//            emit(null)
//        } finally {
//            outputStream.flush()
//            outputStream.close()
//        }
//    }.flowOn(Dispatchers.IO)
}