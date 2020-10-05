package app.box.pokemon.data

import app.box.pokemon.BuildConfig
import app.box.pokemon.data.enteties.ApiPaginationAnswer
import app.box.pokemon.data.enteties.ApiNamedResource
import app.box.pokemon.data.enteties.PokemonInfo
import app.box.pokemon.data.source.ApiDataSource

const val ID_PLACEHOLDER = "{id}"

class RepositoryImpl(
    private val apiDataSource: ApiDataSource
) : Repository {

    override fun getPokemonImageUrl(pokemonId: String) =
        BuildConfig.POKEMON_IMAGE_API_PLACEHOLDER.replace(ID_PLACEHOLDER, pokemonId)

    override fun getTopPokemons(): ApiPaginationAnswer<ApiNamedResource> {
        return apiDataSource.getTopPokemons()
    }

    override fun getPokemonById(id: String): PokemonInfo? {
        return apiDataSource.getPokemonById(id)
    }

    override fun searchPokemons() {
        TODO("Not implemented Yet")
    }
}