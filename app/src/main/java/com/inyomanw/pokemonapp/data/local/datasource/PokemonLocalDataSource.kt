package com.inyomanw.pokemonapp.data.local.datasource

import com.couchbase.lite.DataSource
import com.couchbase.lite.Expression
import com.couchbase.lite.MutableArray
import com.couchbase.lite.MutableDocument
import com.couchbase.lite.QueryBuilder
import com.couchbase.lite.SelectResult
import com.inyomanw.pokemonapp.data.local.CouchbaseManager
import com.inyomanw.pokemonapp.data.local.model.AbilityDocument
import com.inyomanw.pokemonapp.data.local.model.PokemonDetailDocument
import com.inyomanw.pokemonapp.data.local.model.PokemonDocument
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonLocalDataSource @Inject constructor(
    private val couchbaseManager: CouchbaseManager
) {

    fun savePokemonList(pokemons: List<PokemonDocument>) {
        val collection = couchbaseManager.pokemonListCollection
        pokemons.forEach { pokemon ->
            val docId = "pokemon_${pokemon.name}"
            val doc = MutableDocument(docId).apply {
                setString("name", pokemon.name)
                setString("url", pokemon.url)
                setInt("offset", pokemon.offset)
            }
            collection.save(doc)
        }
    }

    fun getPokemonListByOffset(offset: Int): List<PokemonDocument> {
        val collection = couchbaseManager.pokemonListCollection
        val query = QueryBuilder
            .select(SelectResult.all())
            .from(DataSource.collection(collection))
            .where(Expression.property("offset").equalTo(Expression.intValue(offset)))

        return query.execute().use { result ->
            result.map { row ->
                val dict = row.getDictionary(collection.name) ?: return@map null
                PokemonDocument(
                    name = dict.getString("name") ?: "",
                    url = dict.getString("url") ?: "",
                    offset = dict.getInt("offset")
                )
            }.filterNotNull()
        }
    }

    fun savePokemonDetail(detail: PokemonDetailDocument) {
        val collection = couchbaseManager.pokemonDetailCollection
        val docId = "detail_${detail.id}"
        val abilitiesArray = MutableArray().apply {
            detail.abilities.forEachIndexed { index, ability ->
                addDictionary(
                    com.couchbase.lite.MutableDictionary().apply {
                        setString("abilityName", ability.abilityName)
                        setBoolean("isHidden", ability.isHidden)
                    }
                )
            }
        }
        val doc = MutableDocument(docId).apply {
            setInt("id", detail.id)
            setString("name", detail.name)
            setArray("abilities", abilitiesArray)
        }
        collection.save(doc)
    }

    fun getPokemonDetail(id: Int): PokemonDetailDocument? {
        val collection = couchbaseManager.pokemonDetailCollection
        val doc = collection.getDocument("detail_$id") ?: return null
        val abilitiesArray = doc.getArray("abilities") ?: return PokemonDetailDocument(
            id = doc.getInt("id"),
            name = doc.getString("name") ?: "",
            abilities = emptyList()
        )
        val abilities = (0 until abilitiesArray.count()).mapNotNull { i ->
            val dict = abilitiesArray.getDictionary(i) ?: return@mapNotNull null
            AbilityDocument(
                abilityName = dict.getString("abilityName") ?: "",
                isHidden = dict.getBoolean("isHidden")
            )
        }
        return PokemonDetailDocument(
            id = doc.getInt("id"),
            name = doc.getString("name") ?: "",
            abilities = abilities
        )
    }

}
