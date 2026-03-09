package com.inyomanw.pokemonapp.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.inyomanw.pokemonapp.data.local.UserLocalDataSource
import com.inyomanw.pokemonapp.data.local.model.UserDocument
import com.inyomanw.pokemonapp.data.mapper.toPokemonDetailModel
import com.inyomanw.pokemonapp.data.paging.AppPagingSource
import com.inyomanw.pokemonapp.data.paging.AppPagingSource.Companion.LIMIT
import com.inyomanw.pokemonapp.data.remote.api.ApiService
import com.inyomanw.pokemonapp.domain.Repository
import com.inyomanw.pokemonapp.domain.model.PokemonDetailModel
import com.inyomanw.pokemonapp.domain.model.PokemonModel
import com.inyomanw.pokemonapp.domain.model.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.security.MessageDigest
import java.util.UUID
import javax.inject.Inject

class RepositoryImplementation @Inject constructor(
    private val apiService: ApiService,
    private val userLocalDataSource: UserLocalDataSource
) : Repository {

    override fun getListPokemon(): Flow<PagingData<PokemonModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = LIMIT,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                AppPagingSource(apiService)
            }
        ).flow
    }

    override suspend fun getPokemonDetail(id: Int): PokemonDetailModel {
        return apiService.getDetailPokemon(id).toPokemonDetailModel()
    }

    override fun registerUser(user: UserModel, password: String): Flow<Result<Unit>> = flow {
        if (userLocalDataSource.isUsernameExists(user.username)) {
            emit(Result.failure(Exception("Username '${user.username}' sudah terdaftar")))
            return@flow
        }
        val document = UserDocument(
            id = UUID.randomUUID().toString(),
            username = user.username,
            passwordHash = hashPassword(password),
            fullName = user.fullName,
            address = user.address
        )
        userLocalDataSource.saveUser(document)
        emit(Result.success(Unit))
    }

    override fun loginUser(username: String, password: String): Flow<Result<UserModel>> = flow {
        val userDoc = userLocalDataSource.findByUsername(username)
        if (userDoc == null) {
            emit(Result.failure(Exception("Username tidak ditemukan")))
            return@flow
        }
        if (userDoc.passwordHash != hashPassword(password)) {
            emit(Result.failure(Exception("Password salah")))
            return@flow
        }
        emit(Result.success(UserModel(
            username = userDoc.username,
            fullName = userDoc.fullName,
            address = userDoc.address
        )))
    }

    private fun hashPassword(password: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(password.toByteArray(Charsets.UTF_8))
        return hashBytes.joinToString("") { "%02x".format(it) }
    }
}