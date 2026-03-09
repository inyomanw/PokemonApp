package com.inyomanw.pokemonapp.domain.usecase

import com.inyomanw.pokemonapp.domain.Repository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke() {
        repository.logout()
    }
}
