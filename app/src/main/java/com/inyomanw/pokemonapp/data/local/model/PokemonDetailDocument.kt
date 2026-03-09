package com.inyomanw.pokemonapp.data.local.model

data class PokemonDetailDocument(
    val id: Int,
    val name: String,
    val abilities: List<AbilityDocument>
)

data class AbilityDocument(
    val abilityName: String,
    val isHidden: Boolean
)
