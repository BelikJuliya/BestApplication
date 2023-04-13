package com.example.myapplication.films_list_flow

import com.example.domain.model.FilmDomainModel
import com.example.myapplication.base.BaseRecyclerAdapter

class FilmsAdapter(
    save: (event: FilmDomainModel) -> Unit = {},
    remove: (event: FilmDomainModel) -> Unit = {},
) : BaseRecyclerAdapter(
    listOf(
        FilmsDelegate(save, remove),
//        ErrorDelegate(),
//        EmptyListDelegate(),
//        LoaderDelegate()
    )
)