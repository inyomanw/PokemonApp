package com.inyomanw.pokemonapp.data.remote.model

import com.google.gson.annotations.SerializedName
import com.inyomanw.pokemonapp.domain.model.PokemonModel

data class PokemonResponse(
    @SerializedName("results")
    val result: List<PokemonModel>
)