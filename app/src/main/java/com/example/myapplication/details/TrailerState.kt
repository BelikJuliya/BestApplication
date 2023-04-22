package com.example.myapplication.details

import com.example.domain.model.YouTubeTrailerDomainModel

sealed class TrailerState {
    open class Success(val data: YouTubeTrailerDomainModel) : TrailerState()
    object  Loading : TrailerState()
    object Error : TrailerState()
}