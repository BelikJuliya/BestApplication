package com.example.data.remote

interface IResponse<T> {

    fun toDomainObject(): T

}