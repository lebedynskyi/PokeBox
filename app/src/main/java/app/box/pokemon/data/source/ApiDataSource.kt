package app.box.pokemon.data.source

import android.net.Uri
import app.box.pokemon.BuildConfig
import app.box.pokemon.data.api.PokemonApiClient
import app.box.pokemon.data.api.enteties.PokemonInfoApiResponse
import app.box.pokemon.data.api.enteties.PokemonSearchApiResponse
import app.box.pokemon.data.enteties.PokemonSearchInfo
import app.box.pokemon.data.enteties.PokemonInfo
import app.box.pokemon.data.enteties.PokemonType
import app.box.pokemon.data.enteties.ValuePair

const val ID_PLACEHOLDER = "{id}"

class ApiDataSource(
    private val apiClient: PokemonApiClient
) {
    suspend fun getTopPokemons(offset: Int, limit: Int): List<PokemonSearchInfo> {
        return mapResult(apiClient.topPokemons(offset, limit).results)
    }

    suspend fun getPokemonById(id: Int): PokemonInfo? {
        return mapResult(apiClient.pokemonById(id))
    }

    private fun mapResult(apiResult: List<PokemonSearchApiResponse>): List<PokemonSearchInfo> {
        return apiResult.map {
            val (pokemonId, pokemonImage) = parsePokemonUrl(it.url)
            PokemonSearchInfo(pokemonId, it.url, it.name.capitalize(), pokemonImage)
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
                ),
                listOfNotNull(
                    apiResult.sprites.back_default,
                    apiResult.sprites.back_female,
                    apiResult.sprites.back_shiny,
                    apiResult.sprites.back_shiny_female,
                    apiResult.sprites.front_default,
                    apiResult.sprites.front_female,
                    apiResult.sprites.front_shiny,
                    apiResult.sprites.front_shiny_female
                )
            )
        }
    }

    private fun parsePokemonUrl(url: String): Pair<Int, String?> {
        val uri = Uri.parse(url)
        val pokemonId = uri.lastPathSegment
        val imageUrl = pokemonId?.let {
            BuildConfig.POKEMON_IMAGE_API_PLACEHOLDER.replace(
                ID_PLACEHOLDER,
                pokemonId
            )
        }

        return (pokemonId?.toInt() ?: -1) to imageUrl
    }
}