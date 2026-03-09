package com.inyomanw.pokemonapp.data.local.datasource

import com.couchbase.lite.MutableDocument
import com.couchbase.lite.QueryBuilder
import com.couchbase.lite.SelectResult
import com.couchbase.lite.DataSource
import com.couchbase.lite.Expression
import com.inyomanw.pokemonapp.data.local.CouchbaseManager
import com.inyomanw.pokemonapp.data.local.model.UserDocument
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserLocalDataSource @Inject constructor(
    private val couchbaseManager: CouchbaseManager
) {
    private val collection get() = couchbaseManager.userCollection

    fun saveUser(userDocument: UserDocument) {
        val doc = MutableDocument(userDocument.id).apply {
            setString("username", userDocument.username)
            setString("passwordHash", userDocument.passwordHash)
            setString("fullName", userDocument.fullName)
            setString("address", userDocument.address)
        }
        collection.save(doc)
    }

    fun findByUsername(username: String): UserDocument? {
        val query = QueryBuilder
            .select(SelectResult.all())
            .from(DataSource.collection(collection))
            .where(Expression.property("username").equalTo(Expression.string(username)))

        query.execute().use { result ->
            val row = result.firstOrNull() ?: return null
            val dict = row.getDictionary(collection.name) ?: return null
            return UserDocument(
                id = dict.getString("id") ?: "",
                username = dict.getString("username") ?: "",
                passwordHash = dict.getString("passwordHash") ?: "",
                fullName = dict.getString("fullName") ?: "",
                address = dict.getString("address") ?: ""
            )
        }
    }

    fun isUsernameExists(username: String): Boolean {
        return findByUsername(username) != null
    }
}
