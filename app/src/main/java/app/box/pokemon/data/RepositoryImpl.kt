package app.box.pokemon.data

import androidx.paging.DataSource
import androidx.paging.PagedList
import app.box.pokemon.data.enteties.PokemonInfo
import app.box.pokemon.data.enteties.PokemonSearchInfo
import app.box.pokemon.data.source.ApiDataSource
import app.box.pokemon.data.source.DBDataSource
import app.box.pokemon.data.source.NetworkStateDataSource
import java.util.concurrent.Executor

class RepositoryImpl(
    private val apiDataSource: ApiDataSource,
    private val dbDataSource: DBDataSource,
    private val networkStateDataSource: NetworkStateDataSource,
    private val backgroundExecutor: Executor,
    private val mainExecutor: Executor
) : Repository {
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
            .setNotifyExecutor(mainExecutor)
            .setFetchExecutor(backgroundExecutor)
            .build()
    }

    override suspend fun getPokemonById(id: String): PokemonInfo? {
        return apiDataSource.getPokemonById(id)
    }
}