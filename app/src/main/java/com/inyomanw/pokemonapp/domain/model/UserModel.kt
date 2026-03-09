package com.inyomanw.pokemonapp.domain.model

data class UserModel(
    val id: Long? = null,
    val username: String,
    val fullName: String,
    val address: String
)