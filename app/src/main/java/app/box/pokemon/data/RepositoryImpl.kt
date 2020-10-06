package app.box.pokemon.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import app.box.pokemon.data.enteties.PokemonInfo
import app.box.pokemon.data.source.*

@ExperimentalPagingApi
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

    override suspend fun getPokemonById(id: Int): PokemonInfo? {
        return apiDataSource.getPokemonById(id)
    }
}