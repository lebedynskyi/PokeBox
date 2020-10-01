package app.box.pokemon.data

import app.box.pokemon.data.model.ApiPaginationAnswer
import app.box.pokemon.data.model.ApiNamedResource

interface Repository {
    fun getTopPokemons(): ApiPaginationAnswer<ApiNamedResource>
    fun getPokemonById()
    fun searchPokemons()
}