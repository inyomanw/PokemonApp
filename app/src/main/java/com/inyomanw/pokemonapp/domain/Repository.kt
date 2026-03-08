package com.inyomanw.pokemonapp.domain

import androidx.paging.PagingData
import com.inyomanw.pokemonapp.domain.model.PokemonModel
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getListPokemon() : Flow<PagingData<PokemonModel>>
}