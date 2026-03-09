package com.inyomanw.pokemonapp.presentation.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inyomanw.pokemonapp.domain.model.UserModel
import com.inyomanw.pokemonapp.domain.usecase.LoginUseCase
import com.inyomanw.pokemonapp.domain.usecase.RegisterUseCase
import com.inyomanw.pokemonapp.presentation.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private val _loginState = MutableStateFlow<UiState<UserModel>>(UiState.Init)
    val loginState: StateFlow<UiState<UserModel>> = _loginState.asStateFlow()

    private val _registerState = MutableStateFlow<UiState<Unit>>(UiState.Init)
    val registerState: StateFlow<UiState<Unit>> = _registerState.asStateFlow()

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _loginState.value = UiState.Loading
            loginUseCase(username, password).collect { result ->
                result.fold(
                    onSuccess = { user -> _loginState.value = UiState.Success(user) },
                    onFailure = { e -> _loginState.value = UiState.Error(e.message ?: "Login gagal") }
                )
            }
        }
    }

    fun register(user: UserModel, password: String) {
        viewModelScope.launch {
            _registerState.value = UiState.Loading
            registerUseCase(user, password).collect { result ->
                result.fold(
                    onSuccess = { _registerState.value = UiState.Success(Unit) },
                    onFailure = { e -> _registerState.value = UiState.Error(e.message ?: "Register gagal") }
                )
            }
        }
    }

    fun resetLoginState() {
        _loginState.value = UiState.Init
    }

    fun resetRegisterState() {
        _registerState.value = UiState.Init
    }
}

