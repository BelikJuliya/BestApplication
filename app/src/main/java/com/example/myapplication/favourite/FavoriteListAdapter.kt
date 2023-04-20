package com.example.myapplication.favourite

import com.example.domain.model.FilmDomainModel
import com.example.myapplication.base.BaseRecyclerAdapter
import com.example.myapplication.base.EmptyListDelegate
import com.example.myapplication.base.ErrorDelegate
import com.example.myapplication.base.LoaderDelegate

class FavoriteListAdapter(
    removeFromSaved: (film: FilmDomainModel) -> Unit,
    navigateToDetails: (id: String, isSaved: Boolean) -> Unit
) : BaseRecyclerAdapter(
    listOf(
        FavouriteFilmDelegate(removeFromSaved, navigateToDetails),
        ErrorDelegate(),
        EmptyListDelegate(),
        LoaderDelegate()
    )
)