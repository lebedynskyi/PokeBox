package app.box.pokemon.data.source

import app.box.pokemon.data.api.PokemonApiClient
import app.box.pokemon.data.model.ApiPaginationAnswer
import app.box.pokemon.data.model.ApiNamedResource

class ApiDataSource(
    val apiClient: PokemonApiClient
) {
    fun getTopPokemons(): ApiPaginationAnswer<ApiNamedResource> {
        return apiClient.top().execute().body()!!
    }
}