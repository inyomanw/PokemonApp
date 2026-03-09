package com.inyomanw.pokemonapp.data.local.model

data class UserDocument(
    val id: String,
    val username: String,
    val passwordHash: String,
    val fullName: String,
    val address: String
)
