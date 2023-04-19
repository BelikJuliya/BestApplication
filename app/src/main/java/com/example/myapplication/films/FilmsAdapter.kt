package com.example.myapplication.films

import android.graphics.Bitmap
import com.example.domain.model.FilmDomainModel
import com.example.myapplication.base.BaseRecyclerAdapter
import com.example.myapplication.base.EmptyListDelegate
import com.example.myapplication.base.ErrorDelegate
import com.example.myapplication.base.LoaderDelegate

class FilmsAdapter(
    saveFilm: (film: FilmDomainModel) -> Unit,
    removeFromSaved: (film: FilmDomainModel) -> Unit,
    navigateToDetails: (id: String) -> Unit
//    downloadImage: (url: String)
) : BaseRecyclerAdapter(
    listOf(
//        FilmsDelegate(saveFilm, removeFromSaved, downloadImage),
        FilmsDelegate(saveFilm, removeFromSaved, navigateToDetails),
        ErrorDelegate(),
        EmptyListDelegate(),
        LoaderDelegate()
    )
)