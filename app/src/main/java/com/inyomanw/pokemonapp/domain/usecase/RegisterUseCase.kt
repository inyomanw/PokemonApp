package com.inyomanw.pokemonapp.domain.usecase

import com.inyomanw.pokemonapp.domain.Repository
import com.inyomanw.pokemonapp.domain.model.UserModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(user: UserModel, password: String): Flow<Result<Unit>> {
        return repository.registerUser(user, password)
    }
}
