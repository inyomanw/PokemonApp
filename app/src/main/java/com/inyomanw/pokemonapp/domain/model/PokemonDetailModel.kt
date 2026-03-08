package com.inyomanw.pokemonapp.domain.model

data class PokemonDetailModel(
    val name: String?,
    val abilityList: List<AbilityModel>?
)

data class AbilityModel(
    val abilityName: String?,
    val isHidden: Boolean?,
)
