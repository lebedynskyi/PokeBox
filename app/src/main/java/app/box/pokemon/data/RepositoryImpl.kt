package app.box.pokemon.data

import androidx.paging.DataSource
import androidx.paging.PagedList
import app.box.pokemon.BuildConfig
import app.box.pokemon.data.enteties.PokemonInfo
import app.box.pokemon.data.enteties.PokemonSearchInfo
import app.box.pokemon.data.source.ApiDataSource
import app.box.pokemon.data.source.DBDataSource
import app.box.pokemon.data.source.NetworkStateDataSource

const val ID_PLACEHOLDER = "{id}"

class RepositoryImpl(
    private val apiDataSource: ApiDataSource,
    private val dbDataSource: DBDataSource,
    private val networkStateDataSource: NetworkStateDataSource
) : Repository {

    override fun getPokemonImageUrl(pokemonId: String) =
        BuildConfig.POKEMON_IMAGE_API_PLACEHOLDER.replace(ID_PLACEHOLDER, pokemonId)

    override suspend fun getTopPokemonsPaged(): PagedList<PokemonSearchInfo> {
        val networkState = networkStateDataSource.getCurrentNetworkState()

        val dataSource: DataSource<Int, PokemonSearchInfo> =
            if (networkState == NetworkStateDataSource.NetworkState.OFFLINE) {
                dbDataSource.getTopPokemonsPaged().create()
            } else {
                apiDataSource
            }

        return PagedList.Builder(dataSource, PAGINATION_LIMIT)
            .setInitialKey(PAGINATION_LIMIT)
            .build()
    }

    override suspend fun getTopPokemons(): List<PokemonSearchInfo> {
        val networkState = networkStateDataSource.getCurrentNetworkState()
        return if (networkState != NetworkStateDataSource.NetworkState.OFFLINE) {
            apiDataSource.getTopPokemons(PAGINATION_OFFSET, PAGINATION_LIMIT).results.also {
                dbDataSource.savePokemons(it)
            }
        } else {
            dbDataSource.getTopPokemons()
        }
    }

    override suspend fun getPokemonById(id: String): PokemonInfo? {
        return apiDataSource.getPokemonById(id)
    }
}