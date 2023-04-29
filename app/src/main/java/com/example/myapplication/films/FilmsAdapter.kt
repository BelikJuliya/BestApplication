package com.example.myapplication.films

import com.example.domain.model.FilmDomainModel
import com.example.myapplication.base.BaseRecyclerAdapter
import com.example.myapplication.base.EmptyListDelegate
import com.example.myapplication.base.ErrorDelegate
import com.example.myapplication.base.LoaderDelegate

class FilmsAdapter(
    save: (film: FilmDomainModel) -> Unit,
    delete: (film: FilmDomainModel) -> Unit,
    navigateToDetails: (id: String) -> Unit
) : BaseRecyclerAdapter(
    listOf(
        FilmsDelegate(save = save, delete = delete, navigateToDetails = navigateToDetails),
        ErrorDelegate(),
        EmptyListDelegate(),
        LoaderDelegate()
    )
)