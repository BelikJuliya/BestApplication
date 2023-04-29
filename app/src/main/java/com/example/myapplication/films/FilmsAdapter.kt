package com.example.myapplication.films

import com.example.domain.model.FilmDomainModel
import com.example.myapplication.base.BaseRecyclerAdapter
import com.example.myapplication.base.EmptyListDelegate
import com.example.myapplication.base.ErrorDelegate
import com.example.myapplication.base.LoaderDelegate

class FilmsAdapter(
    changeSaveState: (film: FilmDomainModel) -> Unit,
    navigateToDetails: (id: String, isSaved: Boolean) -> Unit
) : BaseRecyclerAdapter(
    listOf(
        FilmsDelegate(changeSaveState = changeSaveState, navigateToDetails = navigateToDetails),
        ErrorDelegate(),
        EmptyListDelegate(),
        LoaderDelegate()
    )
)