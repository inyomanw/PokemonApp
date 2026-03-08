package com.inyomanw.pokemonapp.domain.usecase

import com.inyomanw.pokemonapp.domain.Repository
import com.inyomanw.pokemonapp.domain.model.PokemonDetailModel
import javax.inject.Inject

class DetailPokemonUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(id: Int): PokemonDetailModel {
        return repository.getPokemonDetail(id)
    }
}