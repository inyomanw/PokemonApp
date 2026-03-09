package com.inyomanw.pokemonapp.data.local.model

data class PokemonDocument(
    val name: String,
    val url: String,
    val offset: Int  // untuk grouping per halaman
)
