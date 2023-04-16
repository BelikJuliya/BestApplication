package com.example.data.db

interface IEntity<D, E> {

    fun toDomainObject(): D

    fun fromDomainObject(model: D): E
}