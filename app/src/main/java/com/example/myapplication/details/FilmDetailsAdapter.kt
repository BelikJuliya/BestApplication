package com.example.myapplication.details

import com.example.myapplication.base.BaseRecyclerAdapter
import com.example.myapplication.base.EmptyListDelegate
import com.example.myapplication.base.ErrorDelegate
import com.example.myapplication.base.LoaderDelegate

class FilmDetailsAdapter(

//    downloadImage: (url: String) -> Bitmap?
) : BaseRecyclerAdapter(
    listOf(
//        FilmsDelegate(saveFilm, removeFromSaved, downloadImage),
        ActorDelegate(),
        ErrorDelegate(),
        EmptyListDelegate(),
        LoaderDelegate()
    )
)