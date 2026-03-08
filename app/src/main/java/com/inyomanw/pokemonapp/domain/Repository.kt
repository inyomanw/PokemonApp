package com.inyomanw.pokemonapp.domain

import com.inyomanw.pokemonapp.domain.model.PokemonModel
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getListPokemon() : Flow<List<PokemonModel>>
}