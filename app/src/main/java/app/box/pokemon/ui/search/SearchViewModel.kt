package app.box.pokemon.ui.search

import android.net.Uri
import androidx.annotation.StringRes
import androidx.paging.PagedList
import app.box.pokemon.R
import app.box.pokemon.core.BaseViewModel
import app.box.pokemon.data.Repository
import app.box.pokemon.data.enteties.PokemonSearchInfo
import io.uniflow.core.flow.data.UIEvent
import io.uniflow.core.flow.data.UIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SearchViewModel(
    repository: Repository
) : BaseViewModel(repository) {

    init {
        loadFavoritesPokemon()
    }

    fun loadFavoritesPokemon() = action(
        onAction = {
            sendEvent(UIEvent.Loading)
//            val top = loadFavoritesPokemonsAsync()
//            setState { SearchState.ResultState(top) }

            val top = loadFavoritesPokemonsPagedAsync()
            setState { SearchState.ResultStatePaged(top) }
        },
        onError = { error, _ ->
            setState { SearchState.LoadError(error) }
        }
    )

    fun refresh() = loadFavoritesPokemon()

    fun onItemSelected(item: PokemonSearchInfo) = action {
        val uri = Uri.parse(item.url)
        val pokemonId = uri.lastPathSegment
        if (pokemonId != null) {
            sendEvent(SearchEvent.ShowProfile(pokemonId))
        } else {
            sendEvent(SearchEvent.Message(R.string.wrong_url))
        }
    }

    private suspend fun loadFavoritesPokemonsPagedAsync() = withContext(Dispatchers.IO) {
        return@withContext repository.getTopPokemonsPaged()
//        return@withContext repository.getTopPokemonsPaged().map {
//            val uri = Uri.parse(it.url)
//            val pokemonId = uri.lastPathSegment
//            val imageUrl = pokemonId?.let { repository.getPokemonImageUrl(it) }
//            SearchItem(it.name.capitalize(Locale.getDefault()), it.url, imageUrl)
//        }
    }

//    private suspend fun loadFavoritesPokemonsAsync() = withContext(Dispatchers.IO) {
//        return@withContext repository.getTopPokemons().map {
//            val uri = Uri.parse(it.url)
//            val pokemonId = uri.lastPathSegment
//            val imageUrl = pokemonId?.let { repository.getPokemonImageUrl(it) }
//            SearchItem(it.name.capitalize(Locale.getDefault()), it.url, imageUrl)
//        }
//    }

    sealed class SearchState : UIState() {
        class ResultState(val resultList: List<SearchItem>) : SearchState()
        class ResultStatePaged(val pagination: PagedList<PokemonSearchInfo>) : SearchState()
        class LoadError(val error: Exception) : SearchState()
    }

    sealed class SearchEvent : UIEvent() {
        class ShowProfile(val pokemonUrl: String) : SearchEvent()
        class Message(@StringRes message: Int) : SearchEvent()
    }
}