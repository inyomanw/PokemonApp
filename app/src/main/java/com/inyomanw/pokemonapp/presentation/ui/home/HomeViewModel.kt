package com.inyomanw.pokemonapp.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.inyomanw.pokemonapp.domain.usecase.ListPokemonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    listPokemonUseCase: ListPokemonUseCase
) : ViewModel() {

    val pokemonPagingFlow = listPokemonUseCase()
        .cachedIn(viewModelScope)
}
