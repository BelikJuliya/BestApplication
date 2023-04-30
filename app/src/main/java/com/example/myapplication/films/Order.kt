package com.example.myapplication.films

sealed class Order {
    object Alphabet: Order()
    object Rating: Order()
    object Popularity: Order()
    object Count: Order()
}