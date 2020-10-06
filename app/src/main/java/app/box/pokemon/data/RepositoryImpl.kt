package app.box.pokemon.data

import android.os.Handler
import android.os.Looper
import androidx.core.content.ContextCompat
import androidx.paging.DataSource
import androidx.paging.PagedList
import app.box.pokemon.data.enteties.PokemonInfo
import app.box.pokemon.data.enteties.PokemonSearchInfo
import app.box.pokemon.data.source.ApiDataSource
import app.box.pokemon.data.source.DBDataSource
import app.box.pokemon.data.source.NetworkStateDataSource
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class RepositoryImpl(
    private val apiDataSource: ApiDataSource,
    private val dbDataSource: DBDataSource,
    private val networkStateDataSource: NetworkStateDataSource
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
            .setNotifyExecutor(object : Executor {
                val handler = Handler(Looper.getMainLooper())
                override fun execute(command: Runnable?) {
                    handler.post(command)
                }
            })
            .setFetchExecutor(Executors.newSingleThreadExecutor())
            .build()
    }

    override suspend fun getTopPokemons(): List<PokemonSearchInfo> {
        return dbDataSource.getTopPokemons()

//        val networkState = networkStateDataSource.getCurrentNetworkState()
//        return if (networkState != NetworkStateDataSource.NetworkState.OFFLINE) {
//            apiDataSource.getTopPokemons(PAGINATION_OFFSET, PAGINATION_LIMIT).results.also {
//                dbDataSource.savePokemons(it)
//            }
//        } else {
//            dbDataSource.getTopPokemons()
//        }
    }

    override suspend fun getPokemonById(id: String): PokemonInfo? {
        return apiDataSource.getPokemonById(id)
    }
}