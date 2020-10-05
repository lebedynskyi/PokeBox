package app.box.pokemon.data

import app.box.pokemon.data.enteties.ApiPaginationAnswer
import app.box.pokemon.data.enteties.ApiNamedResource
import app.box.pokemon.data.enteties.PokemonInfo

interface Repository {
    fun getPokemonImageUrl(pokemonId: String): String
    fun getTopPokemons(): ApiPaginationAnswer<ApiNamedResource>
    fun getPokemonById(id: String): PokemonInfo?
    fun searchPokemons()
}