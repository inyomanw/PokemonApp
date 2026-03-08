package com.inyomanw.pokemonapp.domain.usecase

import androidx.paging.PagingData
import com.inyomanw.pokemonapp.domain.Repository
import com.inyomanw.pokemonapp.domain.model.PokemonModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ListPokemonUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(): Flow<PagingData<PokemonModel>> {
        return repository.getListPokemon()
    }
}