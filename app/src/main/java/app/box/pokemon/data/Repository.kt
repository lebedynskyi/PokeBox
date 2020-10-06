package app.box.pokemon.data

import androidx.paging.PagedList
import app.box.pokemon.data.enteties.PokemonSearchInfo
import app.box.pokemon.data.enteties.PokemonInfo
import kotlinx.coroutines.flow.Flow

const val PAGINATION_LIMIT = 20
const val PAGINATION_OFFSET = 0

interface Repository {
    fun getPokemonImageUrl(pokemonId: String): String
    suspend fun  getTopPokemons(): List<PokemonSearchInfo>
    suspend fun  getTopPokemonsPaged(): PagedList<PokemonSearchInfo>
    suspend fun  getPokemonById(id: String): PokemonInfo?
}