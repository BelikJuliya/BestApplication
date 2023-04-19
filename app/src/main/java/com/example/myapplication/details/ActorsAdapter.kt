package com.example.myapplication.details

import com.example.myapplication.base.BaseRecyclerAdapter
import com.example.myapplication.base.EmptyListDelegate
import com.example.myapplication.base.ErrorDelegate
import com.example.myapplication.base.LoaderDelegate

class ActorsAdapter : BaseRecyclerAdapter(
    listOf(
        ActorDelegate(),
        ErrorDelegate(),
        EmptyListDelegate(),
        LoaderDelegate()
    )
)