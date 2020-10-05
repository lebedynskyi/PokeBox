package app.box.pokemon.data

import app.box.pokemon.data.enteties.PokemonSearchInfo
import app.box.pokemon.data.enteties.PokemonInfo

interface Repository {
    fun getPokemonImageUrl(pokemonId: String): String
    fun getTopPokemons(): List<PokemonSearchInfo>
    fun getPokemonById(id: String): PokemonInfo?
}