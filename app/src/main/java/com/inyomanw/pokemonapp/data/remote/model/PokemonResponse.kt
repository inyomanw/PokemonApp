package com.inyomanw.pokemonapp.data.remote.model

import com.google.gson.annotations.SerializedName
import com.inyomanw.pokemonapp.domain.model.PokemonModel

data class PokemonResponse(
    @SerializedName("count")
    val count: Int,
    @SerializedName("next")
    val next: String?,
    @SerializedName("previous")
    val previous: String?,
    @SerializedName("results")
    val result: List<PokemonModel>
)