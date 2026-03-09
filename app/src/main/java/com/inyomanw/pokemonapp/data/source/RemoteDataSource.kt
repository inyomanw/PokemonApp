package com.inyomanw.pokemonapp.data.source

import com.inyomanw.pokemonapp.data.remote.api.ApiService
import com.inyomanw.pokemonapp.data.remote.model.PokemonDetailResponse
import com.inyomanw.pokemonapp.data.remote.model.PokemonResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getPokemonList(offset: Int, limit: Int): PokemonResponse {
        return apiService.getPokemon(offset = offset, limit = limit)
    }

    suspend fun getPokemonDetail(id: Int): PokemonDetailResponse {
        return apiService.getDetailPokemon(id)
    }
}
