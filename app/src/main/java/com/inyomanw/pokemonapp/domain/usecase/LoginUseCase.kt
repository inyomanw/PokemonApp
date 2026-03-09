package com.inyomanw.pokemonapp.domain.usecase

import com.inyomanw.pokemonapp.domain.Repository
import com.inyomanw.pokemonapp.domain.model.UserModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(username: String, password: String): Flow<Result<UserModel>> {
        return repository.loginUser(username, password)
    }
}
