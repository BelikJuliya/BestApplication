package com.example.myapplication.splash

sealed class Result {
    open class Success() : Result()
    object Error : Result()
    object Loading : Result()
}