package com.example.myapplication.films_details_flow

import com.example.domain.model.FilmDetailsScreenDomainModel
import com.example.myapplication.base.BaseRecyclerAdapter
import com.example.myapplication.base.EmptyListDelegate
import com.example.myapplication.base.ErrorDelegate
import com.example.myapplication.base.LoaderDelegate
import com.example.myapplication.films_list_flow.FilmsDelegate

class FilmDetailsAdapter(
    saveFilm: (filmDetails: FilmDetailsScreenDomainModel) -> Unit,
    removeFromSaved: (filmDetails: FilmDetailsScreenDomainModel) -> Unit,
//    downloadImage: (url: String) -> Bitmap?
) : BaseRecyclerAdapter(
    listOf(
//        FilmsDelegate(saveFilm, removeFromSaved, downloadImage),
        FilmsDelegate(saveFilm, removeFromSaved),
        ErrorDelegate(),
        EmptyListDelegate(),
        LoaderDelegate()
    )
)