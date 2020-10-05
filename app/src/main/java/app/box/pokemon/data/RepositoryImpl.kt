package app.box.pokemon.data

import app.box.pokemon.data.enteties.ApiPaginationAnswer
import app.box.pokemon.data.enteties.ApiNamedResource
import app.box.pokemon.data.enteties.PokemonInfo
import app.box.pokemon.data.source.ApiDataSource

class RepositoryImpl(
    private val apiDataSource: ApiDataSource
) : Repository {
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