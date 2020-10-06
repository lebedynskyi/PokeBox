package app.box.pokemon.data.source

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import app.box.pokemon.data.enteties.PokemonSearchInfo

const val PAGINATION_LIMIT = 20
const val PAGINATION_OFFSET = 0
const val POKEMONS_MAX_COUNT = 1050

@ExperimentalPagingApi
class PagingPokemonSourceMediator(
    private val apiDataSource: ApiDataSource,
    private val dbDataSource: DBDataSource,
    private val networkStateDataSource: NetworkStateDataSource,
) : RemoteMediator<Int, PokemonSearchInfo>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PokemonSearchInfo>
    ): MediatorResult {
        try {
            if (networkStateDataSource.getCurrentNetworkState() == NetworkStateDataSource.NetworkState.OFFLINE) {
                return MediatorResult.Success(endOfPaginationReached = true)
            }

            Log.d("PAGIN", "Load thread -> $loadType")
            // Get the closest item from PagingState that we want to load data around.
            val apiOffset = when (loadType) {
                LoadType.REFRESH -> PAGINATION_OFFSET
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    // Get saved number of pokemons to use as offset
                    dbDataSource.getTopPokemonSavedNumber()
                }
                else -> PAGINATION_OFFSET
            }

            Log.d("PAGIN", "Load offset -> $apiOffset")

            val items = apiDataSource.getTopPokemons(apiOffset, PAGINATION_LIMIT)

            if (loadType == LoadType.REFRESH) {
                dbDataSource.removePokemons()
            }
            dbDataSource.savePokemons(items)

            return MediatorResult.Success(endOfPaginationReached = items.isEmpty())
        } catch (e: Exception) {
            e.printStackTrace()
            return MediatorResult.Error(e)
        }
    }
}