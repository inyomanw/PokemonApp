package com.inyomanw.pokemonapp.data

import com.inyomanw.pokemonapp.data.remote.api.ApiService
import com.inyomanw.pokemonapp.domain.Repository
import com.inyomanw.pokemonapp.domain.model.PokemonModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositoryImplementation @Inject constructor(
    private val apiService: ApiService
) : Repository {

    override fun getListPokemon(): Flow<List<PokemonModel>> {
        TODO("Not yet implemented")
    }

}