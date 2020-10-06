package app.box.pokemon.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import app.box.pokemon.data.enteties.PokemonInfo
import app.box.pokemon.data.source.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
@FlowPreview
class RepositoryImpl(
    private val apiDataSource: ApiDataSource,
    private val dbDataSource: DBDataSource,
    private val networkStateDataSource: NetworkStateDataSource,
) : Repository {
    override fun getTopPokemonsPaged() = Pager(
        config = PagingConfig(
            pageSize = PAGINATION_LIMIT,
            prefetchDistance = 10,
            initialLoadSize = PAGINATION_LIMIT,
            enablePlaceholders = true
        ),
        remoteMediator = PagingPokemonSourceMediator(
            apiDataSource,
            dbDataSource,
            networkStateDataSource
        )
    ) {
        dbDataSource.getTopPokemonsPaged()
    }.flow

    override fun getPokemonById(id: Int): Flow<PokemonInfo?> {
        return flowOf(dbDataSource.getPokemonById(id),
            flow {
                if (networkStateDataSource.getCurrentNetworkState() != NetworkStateDataSource.NetworkState.OFFLINE) {
                    val pokemon = apiDataSource.getPokemonById(id)
                    if (pokemon != null) {
                        dbDataSource.savePokemon(pokemon)
                    }
                }
            }
        ).flattenMerge()
    }
}