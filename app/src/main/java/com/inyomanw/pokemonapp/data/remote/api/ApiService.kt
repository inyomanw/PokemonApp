package com.inyomanw.pokemonapp.data.remote.api

import com.inyomanw.pokemonapp.data.remote.model.PokemonResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("pokemon")
    suspend fun getPokemon(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): PokemonResponse

}