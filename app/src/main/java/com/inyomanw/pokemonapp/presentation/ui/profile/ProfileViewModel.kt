package com.inyomanw.pokemonapp.presentation.ui.profile

import androidx.lifecycle.ViewModel
import com.inyomanw.pokemonapp.domain.model.UserModel
import com.inyomanw.pokemonapp.domain.usecase.GetLoggedInUserUseCase
import com.inyomanw.pokemonapp.domain.usecase.LogoutUseCase
import com.inyomanw.pokemonapp.presentation.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getLoggedInUserUseCase: GetLoggedInUserUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    private val _profileState = MutableStateFlow<UiState<UserModel>>(UiState.Init)
    val profileState: StateFlow<UiState<UserModel>> = _profileState.asStateFlow()

    init {
        loadUser()
    }

    fun loadUser() {
        val user = getLoggedInUserUseCase()
        _profileState.value = if (user != null) {
            UiState.Success(user)
        } else {
            UiState.Error("User tidak ditemukan")
        }
    }

    fun logout() {
        logoutUseCase()
    }
}
