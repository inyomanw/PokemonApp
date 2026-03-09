package com.inyomanw.pokemonapp.data.local

import android.content.Context
import com.couchbase.lite.Collection
import com.couchbase.lite.CouchbaseLite
import com.couchbase.lite.Database
import com.couchbase.lite.DatabaseConfiguration
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CouchbaseManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val database: Database by lazy {
        CouchbaseLite.init(context)
        val config = DatabaseConfiguration()
        Database("pokemon_app_db", config)
    }

    val userCollection: Collection by lazy {
        database.createCollection("users")
    }

    val pokemonListCollection: Collection by lazy {
        database.createCollection("pokemon_list")
    }

    val pokemonDetailCollection: Collection by lazy {
        database.createCollection("pokemon_detail")
    }
}
