package app.box.pokemon.ui.search

import android.net.Uri
import androidx.annotation.StringRes
import app.box.pokemon.R
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
        val uri = Uri.parse(item.url)
        val pokemonId = uri.lastPathSegment
        if (pokemonId != null) {
            sendEvent(SearchEvent.ShowProfile(pokemonId))
        } else {
            sendEvent(SearchEvent.Message(R.string.wrong_url))
        }
    }

    private suspend fun loadFavoritesPokemonsAsync() = withContext(Dispatchers.IO) {
        return@withContext repository.getTopPokemons().results.map {
            val uri = Uri.parse(it.url)
            val pokemonId = uri.lastPathSegment
            val imageUrl = pokemonId?.let { repository.getPokemonImageUrl(it) }
            SearchItem(it.name.capitalize(Locale.getDefault()), it.url, imageUrl)
        }
    }

    sealed class SearchState : UIState() {
        class ResultState(val resultList: List<SearchItem>) : SearchState()
        class LoadError : SearchState()
    }

    sealed class SearchEvent : UIEvent() {
        class ShowProfile(val pokemonUrl: String) : SearchEvent()
        class Message(@StringRes message: Int) : SearchEvent()
    }
}