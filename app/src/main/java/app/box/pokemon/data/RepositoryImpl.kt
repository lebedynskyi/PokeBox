package app.box.pokemon.data

import app.box.pokemon.data.model.ApiPaginationAnswer
import app.box.pokemon.data.model.ApiNamedResource
import app.box.pokemon.data.model.PokemonInfo
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