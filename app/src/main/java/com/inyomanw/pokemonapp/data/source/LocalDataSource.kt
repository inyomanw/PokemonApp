package com.inyomanw.pokemonapp.data.source

import com.inyomanw.pokemonapp.data.local.datasource.PokemonLocalDataSource
import com.inyomanw.pokemonapp.data.local.datasource.UserLocalDataSource
import com.inyomanw.pokemonapp.data.local.model.PokemonDetailDocument
import com.inyomanw.pokemonapp.data.local.model.PokemonDocument
import com.inyomanw.pokemonapp.data.local.model.UserDocument
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(
    private val pokemonLocalDataSource: PokemonLocalDataSource,
    private val userLocalDataSource: UserLocalDataSource
) {

    fun savePokemonList(pokemons: List<PokemonDocument>) =
        pokemonLocalDataSource.savePokemonList(pokemons)

    fun getPokemonListByOffset(offset: Int): List<PokemonDocument> =
        pokemonLocalDataSource.getPokemonListByOffset(offset)

    fun savePokemonDetail(detail: PokemonDetailDocument) =
        pokemonLocalDataSource.savePokemonDetail(detail)

    fun getPokemonDetail(id: Int): PokemonDetailDocument? =
        pokemonLocalDataSource.getPokemonDetail(id)

    fun saveUser(userDocument: UserDocument) =
        userLocalDataSource.saveUser(userDocument)

    fun findByUsername(username: String): UserDocument? =
        userLocalDataSource.findByUsername(username)

    fun isUsernameExists(username: String): Boolean =
        userLocalDataSource.isUsernameExists(username)
}
