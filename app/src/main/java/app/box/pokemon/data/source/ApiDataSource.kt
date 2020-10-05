package app.box.pokemon.data.source

import app.box.pokemon.data.api.PokemonApiClient
import app.box.pokemon.data.enteties.ApiPaginationAnswer
import app.box.pokemon.data.enteties.PokemonSearchInfo
import app.box.pokemon.data.enteties.PokemonInfo

class ApiDataSource(
    val apiClient: PokemonApiClient
) {
    fun getTopPokemons(): ApiPaginationAnswer<PokemonSearchInfo> {
        return apiClient.top().execute().body()!!
    }

    fun getPokemonById(id: String): PokemonInfo? {
        return apiClient.pokemonById(id).execute().body()
    }
}