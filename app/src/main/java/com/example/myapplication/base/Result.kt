package com.example.myapplication.base

sealed class Result {
    open class Success(data: Any) : Result()
    object Error : Result()
    object Loading : Result()
}