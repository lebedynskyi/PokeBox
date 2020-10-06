package app.box.pokemon.data

import androidx.paging.PagedList
import app.box.pokemon.data.enteties.PokemonSearchInfo
import app.box.pokemon.data.enteties.PokemonInfo

const val PAGINATION_LIMIT = 20
const val PAGINATION_OFFSET = 0

interface Repository {
    suspend fun  getTopPokemonsPaged(): PagedList<PokemonSearchInfo>
    suspend fun  getPokemonById(id: String): PokemonInfo?
}