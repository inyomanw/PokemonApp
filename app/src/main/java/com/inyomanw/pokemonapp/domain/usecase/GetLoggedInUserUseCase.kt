package com.inyomanw.pokemonapp.domain.usecase

import com.inyomanw.pokemonapp.domain.Repository
import com.inyomanw.pokemonapp.domain.model.UserModel
import javax.inject.Inject

class GetLoggedInUserUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(): UserModel? {
        return repository.getLoggedInUser()
    }
}
