package com.inyomanw.pokemonapp.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.inyomanw.pokemonapp.domain.model.PokemonModel
import com.inyomanw.pokemonapp.domain.usecase.ListPokemonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    listPokemonUseCase: ListPokemonUseCase
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val pokemonPagingFlow: Flow<PagingData<PokemonModel>> = _searchQuery
        .flatMapLatest { query ->
            listPokemonUseCase().map { pagingData ->
                if (query.isBlank()) {
                    pagingData
                } else {
                    pagingData.filter { pokemon ->
                        pokemon.name?.contains(query.trim(), ignoreCase = true) == true
                    }
                }
            }
        }
        .cachedIn(viewModelScope)

    fun searchPokemon(query: String) {
        _searchQuery.value = query
    }
}
