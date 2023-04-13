package com.example.myapplication.films_list_flow

import android.graphics.Bitmap
import com.example.domain.model.FilmDomainModel
import com.example.myapplication.base.BaseRecyclerAdapter

class FilmsAdapter(
    saveFilm: (film: FilmDomainModel) -> Unit,
    removeFromSaved: (film: FilmDomainModel) -> Unit,
    downloadImage: (url: String) -> Bitmap?
) : BaseRecyclerAdapter(
    listOf(
        FilmsDelegate(saveFilm, removeFromSaved, downloadImage),
//        ErrorDelegate(),
//        EmptyListDelegate(),
//        LoaderDelegate()
    )
)