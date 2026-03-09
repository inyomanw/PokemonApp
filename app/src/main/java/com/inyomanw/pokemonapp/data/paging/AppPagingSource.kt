package com.inyomanw.pokemonapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.inyomanw.pokemonapp.data.local.model.PokemonDocument
import com.inyomanw.pokemonapp.data.source.LocalDataSource
import com.inyomanw.pokemonapp.data.source.RemoteDataSource
import com.inyomanw.pokemonapp.domain.model.PokemonModel

class AppPagingSource(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
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

        val cached = localDataSource.getPokemonListByOffset(page)
        if (cached.isNotEmpty()) {
            val pokemons = cached.map { PokemonModel(name = it.name, url = it.url) }
            return LoadResult.Page(
                data = pokemons,
                prevKey = if (page == 0) null else page - LIMIT,
                nextKey = if (cached.size < LIMIT) null else page + LIMIT
            )
        }

        return try {
            val response = remoteDataSource.getPokemonList(offset = page, limit = LIMIT)
            localDataSource.savePokemonList(
                response.result.map { PokemonDocument(name = it.name ?: "", url = it.url ?: "", offset = page) }
            )
            LoadResult.Page(
                data = response.result,
                prevKey = if (page == 0) null else page - LIMIT,
                nextKey = if (response.next == null) null else page + LIMIT
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}