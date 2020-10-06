package app.box.pokemon.data.source

import androidx.paging.PageKeyedDataSource
import app.box.pokemon.data.PAGINATION_LIMIT
import app.box.pokemon.data.PAGINATION_OFFSET
import app.box.pokemon.data.api.PokemonApiClient
import app.box.pokemon.data.enteties.ApiPaginationAnswer
import app.box.pokemon.data.enteties.PokemonSearchInfo
import app.box.pokemon.data.enteties.PokemonInfo
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// TODO use abstraction to avoid import of Repository ?

class ApiDataSource(
    private val apiClient: PokemonApiClient
) : PageKeyedDataSource<Int, PokemonSearchInfo>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, PokemonSearchInfo>
    ) {
        GlobalScope.launch {
            val pagination = apiClient.top(PAGINATION_OFFSET, PAGINATION_LIMIT)
            callback.onResult(pagination.results, 0, PAGINATION_LIMIT)
        }
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, PokemonSearchInfo>
    ) {
        GlobalScope.launch {
            val pagination = apiClient.top(params.key, PAGINATION_LIMIT)
            callback.onResult(pagination.results, params.key - PAGINATION_LIMIT)
        }
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, PokemonSearchInfo>
    ) {
        GlobalScope.launch {
            val pagination = apiClient.top(params.key, PAGINATION_LIMIT)
            callback.onResult(pagination.results, params.key + PAGINATION_LIMIT)
        }
    }

    suspend fun getTopPokemons(offset: Int, limit: Int): ApiPaginationAnswer<PokemonSearchInfo> {
        return apiClient.top(offset, limit)
    }

    suspend fun getPokemonById(id: String): PokemonInfo? {
        return apiClient.pokemonById(id)
    }
}