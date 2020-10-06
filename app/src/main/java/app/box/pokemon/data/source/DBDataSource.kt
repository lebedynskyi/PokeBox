package app.box.pokemon.data.source

import androidx.paging.PagingSource
import app.box.pokemon.data.db.PokemonDatabase
import app.box.pokemon.data.enteties.PokemonSearchInfo

class DBDataSource(
    private val pokemonDatabase: PokemonDatabase
) {
    fun getTopPokemonsPaged(): PagingSource<Int, PokemonSearchInfo> {
        return pokemonDatabase.pokemonDao().getTopPokemonsPaging()
    }

    fun savePokemons(pokemons: List<PokemonSearchInfo>) {
        pokemonDatabase.pokemonDao().saveTopPokemons(pokemons)
    }
}