package com.inyomanw.pokemonapp.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.inyomanw.pokemonapp.data.mapper.toPokemonDetailModel
import com.inyomanw.pokemonapp.data.paging.AppPagingSource
import com.inyomanw.pokemonapp.data.paging.AppPagingSource.Companion.LIMIT
import com.inyomanw.pokemonapp.data.remote.api.ApiService
import com.inyomanw.pokemonapp.domain.Repository
import com.inyomanw.pokemonapp.domain.model.PokemonDetailModel
import com.inyomanw.pokemonapp.domain.model.PokemonModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositoryImplementation @Inject constructor(
    private val apiService: ApiService
) : Repository {

    override fun getListPokemon(): Flow<PagingData<PokemonModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = LIMIT,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                AppPagingSource(apiService)
            }
        ).flow
    }


    override suspend fun getPokemonDetail(id: Int): PokemonDetailModel {
        return apiService.getDetailPokemon(id).toPokemonDetailModel()
    }

}