package com.inyomanw.pokemonapp.presentation.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inyomanw.pokemonapp.domain.model.PokemonDetailModel
import com.inyomanw.pokemonapp.domain.usecase.DetailPokemonUseCase
import com.inyomanw.pokemonapp.presentation.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailPokemonViewModel @Inject constructor(
    private val detailPokemonUseCase: DetailPokemonUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow<UiState<PokemonDetailModel>>(UiState.Init)
    val uiState: StateFlow<UiState<PokemonDetailModel>> = _uiState.asStateFlow()

    fun getPokemonDetail(id: Int) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val result = detailPokemonUseCase(id)
                _uiState.value = UiState.Success(result)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Terjadi kesalahan")
            }
        }
    }
}