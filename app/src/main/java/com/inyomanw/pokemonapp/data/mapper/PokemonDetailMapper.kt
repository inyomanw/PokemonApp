package com.inyomanw.pokemonapp.data.mapper

import com.inyomanw.pokemonapp.data.remote.model.Ability
import com.inyomanw.pokemonapp.data.remote.model.PokemonDetailResponse
import com.inyomanw.pokemonapp.domain.model.AbilityModel
import com.inyomanw.pokemonapp.domain.model.PokemonDetailModel

fun Ability.toAbilityModel(): AbilityModel {
    return AbilityModel(abilityName = this.ability?.name, isHidden = this.isHidden)
}

fun PokemonDetailResponse.toPokemonDetailModel(): PokemonDetailModel {
    return PokemonDetailModel(
        name = this.name ?: "",
        abilityList = this.abilities?.map { it.toAbilityModel() }
    )
}