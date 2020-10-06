package app.box.pokemon.data.source

import android.net.Uri
import android.util.Log
import androidx.paging.PageKeyedDataSource
import app.box.pokemon.BuildConfig
import app.box.pokemon.data.PAGINATION_LIMIT
import app.box.pokemon.data.PAGINATION_OFFSET
import app.box.pokemon.data.api.PokemonApiClient
import app.box.pokemon.data.api.enteties.PokemonInfoApiResponse
import app.box.pokemon.data.api.enteties.PokemonSearchApiResponse
import app.box.pokemon.data.enteties.PokemonSearchInfo
import app.box.pokemon.data.enteties.PokemonInfo
import app.box.pokemon.data.enteties.PokemonType
import app.box.pokemon.data.enteties.ValuePair
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// TODO notify end of pagination
const val ID_PLACEHOLDER = "{id}"

class ApiDataSource(
    private val apiClient: PokemonApiClient
) : PageKeyedDataSource<Int, PokemonSearchInfo>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, PokemonSearchInfo>
    ) {
        Log.d("LOAD", "initial $params")
        GlobalScope.launch {
            val pagination = apiClient.topPokemons(PAGINATION_OFFSET, PAGINATION_LIMIT)
            callback.onResult(mapResult(pagination.results), 0, PAGINATION_LIMIT)
        }
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, PokemonSearchInfo>
    ) {
        Log.d("LOAD", "more $params")
        GlobalScope.launch {
            val pagination = apiClient.topPokemons(params.key, PAGINATION_LIMIT)
            callback.onResult(mapResult(pagination.results), params.key - PAGINATION_LIMIT)
        }
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, PokemonSearchInfo>
    ) {
        Log.d("LOAD", "after $params")
        GlobalScope.launch {
            val pagination = apiClient.topPokemons(params.key, PAGINATION_LIMIT)
            callback.onResult(mapResult(pagination.results), params.key + PAGINATION_LIMIT)
        }
    }

    suspend fun getPokemonById(id: String): PokemonInfo? {
        return mapResult(apiClient.pokemonById(id))
    }

    private fun mapResult(apiResult: List<PokemonSearchApiResponse>): List<PokemonSearchInfo> {
        return apiResult.map {
            val (pokemonId, pokemonImage) = parsePokemonUrl(it.url)
            PokemonSearchInfo(pokemonId.orEmpty(), it.url, it.name.capitalize(), pokemonImage)
        }
    }

    private fun mapResult(apiResult: PokemonInfoApiResponse?): PokemonInfo? {
        return apiResult?.let { pokemon ->
            PokemonInfo(
                pokemon.id,
                pokemon.name.capitalize(),
                pokemon.height,
                pokemon.weight,
                pokemon.types.map { PokemonType(it.slot, ValuePair(it.type.url, it.type.name)) },
                BuildConfig.POKEMON_IMAGE_API_PLACEHOLDER.replace(
                    ID_PLACEHOLDER,
                    pokemon.id.toString()
                )
            )
        }
    }

    private fun parsePokemonUrl(url: String): Pair<String?, String?> {
        val uri = Uri.parse(url)
        val pokemonId = uri.lastPathSegment
        val imageUrl = pokemonId?.let {
            BuildConfig.POKEMON_IMAGE_API_PLACEHOLDER.replace(
                ID_PLACEHOLDER,
                pokemonId
            )
        }

        return pokemonId to imageUrl
    }
}