package com.inyomanw.pokemonapp.presentation.ui.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inyomanw.pokemonapp.domain.model.PokemonDetailModel
import com.inyomanw.pokemonapp.domain.usecase.DetailPokemonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailPokemonViewModel @Inject constructor(
    private val detailPokemonUseCase: DetailPokemonUseCase
): ViewModel() {

    var uiState by mutableStateOf<PokemonDetailModel?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    fun getPokemonDetail(id: Int) {
        viewModelScope.launch {
            isLoading = true
            uiState = detailPokemonUseCase(id)
            isLoading = false
        }
    }
}