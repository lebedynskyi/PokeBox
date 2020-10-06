package app.box.pokemon.data.source

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import app.box.pokemon.data.enteties.PokemonSearchInfo

const val PAGINATION_LIMIT = 20
const val PAGINATION_OFFSET = 0

@ExperimentalPagingApi
class PagingPokemonSourceMediator(
    private val apiDataSource: ApiDataSource,
    private val dbDataSource: DBDataSource,
    private val networkStateDataSource: NetworkStateDataSource,
) : RemoteMediator<Int, PokemonSearchInfo>() {
    override suspend fun load(loadType: LoadType, state: PagingState<Int, PokemonSearchInfo>): MediatorResult {
       return try {
           MediatorResult.Error(NotImplementedError("Work in progress"))
       } catch (e: Exception) {
           e.printStackTrace()
           MediatorResult.Error(e)
       }
    }
}