package com.inyomanw.pokemonapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.inyomanw.pokemonapp.data.remote.api.ApiService
import com.inyomanw.pokemonapp.domain.model.PokemonModel
import javax.inject.Inject

class AppPagingSource @Inject constructor(
    private val apiService: ApiService
) : PagingSource<Int, PokemonModel>() {

    companion object {
        const val STARTING_PAGE_INDEX = 0
        const val LIMIT = 10
    }

    override fun getRefreshKey(state: PagingState<Int, PokemonModel>): Int? {
        return state.anchorPosition?.let { pos ->
            state.closestPageToPosition(pos)?.prevKey?.plus(LIMIT)
                ?: state.closestPageToPosition(pos)?.nextKey?.minus(LIMIT)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonModel> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = apiService.getPokemon(offset = page, limit = LIMIT)
            val pokemons = response.result

            LoadResult.Page(
                data = pokemons,
                prevKey = if (page == 0) null else page - LIMIT,
                nextKey = if (response.next == null) null else page + LIMIT
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}