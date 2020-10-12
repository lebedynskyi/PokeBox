package app.box.pokemon.data.source

import androidx.paging.PagingSource
import app.box.pokemon.data.db.PokemonDatabase
import app.box.pokemon.data.enteties.PokemonInfo
import app.box.pokemon.data.enteties.PokemonSearchInfo
import kotlinx.coroutines.flow.Flow

class DBDataSource(
    private val pokemonDatabase: PokemonDatabase
) {
    fun getTopPokemonsPaged(): PagingSource<Int, PokemonSearchInfo> {
        return pokemonDatabase.pokemonDao().getTopPokemonsPaging()
    }

    fun searchPokemon(query: String): PagingSource<Int, PokemonSearchInfo> {
        return pokemonDatabase.pokemonDao().searchPokemon(query)
    }

    suspend fun getTopPokemonSavedNumber(): Int {
        return pokemonDatabase.pokemonDao().getTopPokemonCount()
    }

    suspend fun savePokemons(pokemons: List<PokemonSearchInfo>) {
        pokemonDatabase.pokemonDao().saveTopPokemons(pokemons)
    }

    suspend fun removePokemons() {
        pokemonDatabase.pokemonDao().clearPokemons()
    }

    fun getPokemonById(id: Int) : Flow<PokemonInfo?> {
        return pokemonDatabase.pokemonDao().getPokemonInfo(id)
    }

    fun savePokemon(pokemon: PokemonInfo) {
        pokemonDatabase.pokemonDao().savePokemonInfo(pokemon)
    }
}