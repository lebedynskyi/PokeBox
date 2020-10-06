package app.box.pokemon.data.source

import androidx.paging.PagingSource
import app.box.pokemon.data.db.PokemonDatabase
import app.box.pokemon.data.enteties.PokemonSearchInfo

class DBDataSource(
    val pokemonDatabase: PokemonDatabase
) {
    fun getTopPokemonsPaged(): PagingSource<Int, PokemonSearchInfo> {
        return pokemonDatabase.pokemonDao().getTopPokemonsPaging()
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
}