package com.inyomanw.pokemonapp.domain

import androidx.paging.PagingData
import com.inyomanw.pokemonapp.domain.model.PokemonDetailModel
import com.inyomanw.pokemonapp.domain.model.PokemonModel
import com.inyomanw.pokemonapp.domain.model.UserModel
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getListPokemon() : Flow<PagingData<PokemonModel>>
    suspend fun getPokemonDetail(id: Int): PokemonDetailModel
    fun registerUser(user: UserModel, password: String): Flow<Result<Unit>>
    fun loginUser(username: String, password: String): Flow<Result<UserModel>>
    fun getLoggedInUser(): UserModel?
    fun logout()
}