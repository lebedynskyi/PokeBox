package app.box.pokemon.data

import app.box.pokemon.data.model.ApiPaginationAnswer
import app.box.pokemon.data.model.ApiNamedResource
import app.box.pokemon.data.model.PokemonInfo

interface Repository {
    fun getTopPokemons(): ApiPaginationAnswer<ApiNamedResource>
    fun getPokemonById(id: String) : PokemonInfo?
    fun searchPokemons()
}