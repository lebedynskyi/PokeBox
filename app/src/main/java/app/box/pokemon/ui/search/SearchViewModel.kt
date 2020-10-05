package app.box.pokemon.ui.search

import app.box.pokemon.core.BaseViewModel
import app.box.pokemon.data.Repository
import io.uniflow.core.flow.data.UIEvent
import io.uniflow.core.flow.data.UIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class SearchViewModel(
    repository: Repository
) : BaseViewModel(repository) {

    init {
        loadFavoritesPokemon()
    }

    fun loadFavoritesPokemon() = action(
        onAction = {
            sendEvent(UIEvent.Loading)
            val top = loadFavoritesPokemonsAsync()
            setState { SearchState.ResultState(top) }
        },
        onError = { _, _ ->
            setState { SearchState.LoadError() }
        }
    )

    fun refresh() = loadFavoritesPokemon()

    fun onItemSelected(item: SearchItem) = action {
        sendEvent(SearchEvent.ShowProfile(item.url))
    }

    private suspend fun loadFavoritesPokemonsAsync() = withContext(Dispatchers.IO) {
        return@withContext repository.getTopPokemons().results.map {
            SearchItem(it.name.capitalize(Locale.getDefault()), it.url)
        }
    }

    sealed class SearchState : UIState() {
        class ResultState(val resultList: List<SearchItem>) : SearchState()
        class LoadError : SearchState()
    }

    sealed class SearchEvent : UIEvent() {
        class ShowProfile(val pokemonUrl: String) : SearchEvent()
    }
}