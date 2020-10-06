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
        config = PagingConfig(PAGINATION_LIMIT),
        remoteMediator = PagingPokemonSourceMediator(
            apiDataSource,
            dbDataSource,
            networkStateDataSource
        )
    ) {
        dbDataSource.getTopPokemonsPaged()
    }.flow

    override suspend fun getPokemonById(id: String): PokemonInfo? {
        return apiDataSource.getPokemonById(id)
    }
}