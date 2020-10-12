package app.box.pokemon.data

import androidx.paging.PagingData
import app.box.pokemon.data.enteties.PokemonInfo
import app.box.pokemon.data.enteties.PokemonSearchInfo
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun  getTopPokemonsPaged(): Flow<PagingData<PokemonSearchInfo>>
    fun  searchCachedPokemon(query: String): Flow<PagingData<PokemonSearchInfo>>
    fun  getPokemonById(id: Int): Flow<PokemonInfo?>
}