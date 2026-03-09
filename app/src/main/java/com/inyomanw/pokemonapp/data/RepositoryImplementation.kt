package com.inyomanw.pokemonapp.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.inyomanw.pokemonapp.data.local.SessionManager
import com.inyomanw.pokemonapp.data.local.model.AbilityDocument
import com.inyomanw.pokemonapp.data.local.model.PokemonDetailDocument
import com.inyomanw.pokemonapp.data.local.model.UserDocument
import com.inyomanw.pokemonapp.data.mapper.toPokemonDetailModel
import com.inyomanw.pokemonapp.data.paging.AppPagingSource
import com.inyomanw.pokemonapp.data.paging.AppPagingSource.Companion.LIMIT
import com.inyomanw.pokemonapp.data.source.LocalDataSource
import com.inyomanw.pokemonapp.data.source.RemoteDataSource
import com.inyomanw.pokemonapp.domain.Repository
import com.inyomanw.pokemonapp.domain.model.AbilityModel
import com.inyomanw.pokemonapp.domain.model.PokemonDetailModel
import com.inyomanw.pokemonapp.domain.model.PokemonModel
import com.inyomanw.pokemonapp.domain.model.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.security.MessageDigest
import java.util.UUID
import javax.inject.Inject

class RepositoryImplementation @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val sessionManager: SessionManager
) : Repository {

    override fun getListPokemon(): Flow<PagingData<PokemonModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = LIMIT,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                AppPagingSource(remoteDataSource, localDataSource)
            }
        ).flow
    }

    override suspend fun getPokemonDetail(id: Int): PokemonDetailModel {
        val cached = localDataSource.getPokemonDetail(id)
        if (cached != null) {
            return PokemonDetailModel(
                name = cached.name,
                abilityList = cached.abilities.map {
                    AbilityModel(abilityName = it.abilityName, isHidden = it.isHidden)
                }
            )
        }

        try {
            val response = remoteDataSource.getPokemonDetail(id)
            val model = response.toPokemonDetailModel()
            localDataSource.savePokemonDetail(
                PokemonDetailDocument(
                    id = id,
                    name = model.name ?: "",
                    abilities = model.abilityList?.map {
                        AbilityDocument(
                            abilityName = it.abilityName ?: "",
                            isHidden = it.isHidden ?: false
                        )
                    } ?: emptyList()
                )
            )
            return model
        } catch (e: Exception) {
            throw Exception("Periksa koneksi internet Anda.", e)
        }
    }

    override fun registerUser(user: UserModel, password: String): Flow<Result<Unit>> = flow {
        if (localDataSource.isUsernameExists(user.username)) {
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
        localDataSource.saveUser(document)
        emit(Result.success(Unit))
    }

    override fun loginUser(username: String, password: String): Flow<Result<UserModel>> = flow {
        val userDoc = localDataSource.findByUsername(username)
        if (userDoc == null) {
            emit(Result.failure(Exception("Username tidak ditemukan")))
            return@flow
        }
        if (userDoc.passwordHash != hashPassword(password)) {
            emit(Result.failure(Exception("Password salah")))
            return@flow
        }
        val user = UserModel(
            username = userDoc.username,
            fullName = userDoc.fullName,
            address = userDoc.address
        )
        sessionManager.saveSession(username)
        emit(Result.success(user))
    }

    override fun getLoggedInUser(): UserModel? {
        val username = sessionManager.getLoggedInUsername() ?: return null
        val doc = localDataSource.findByUsername(username) ?: return null
        return UserModel(
            username = doc.username,
            fullName = doc.fullName,
            address = doc.address
        )
    }

    override fun logout() {
        sessionManager.clearSession()
    }

    private fun hashPassword(password: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(password.toByteArray(Charsets.UTF_8))
        return hashBytes.joinToString("") { "%02x".format(it) }
    }
}