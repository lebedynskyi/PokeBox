package app.box.pokemon.data.source

import app.box.pokemon.data.db.PokemonDatabase
import app.box.pokemon.data.enteties.PokemonSearchInfo

class DBDataSource(
    private val pokemonDatabase: PokemonDatabase
) {
    fun getTopPokemons(): List<PokemonSearchInfo> {
        return pokemonDatabase.pokemonDao().getTopPokemons()
    }

    fun savePokemons(pokemons: List<PokemonSearchInfo>) {
        pokemonDatabase.pokemonDao().saveTopPokemons(pokemons)
    }
}