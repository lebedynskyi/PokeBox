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

class SearchViewModel(
    repository: Repository
) : BaseViewModel(repository) {

    fun loadFavoritesPokemon() = action(
        onAction = {
            sendEvent(UIEvent.Loading)
            val top =repository.getTopPokemonsPaged()
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

    sealed class SearchState : UIState() {
        class ResultStatePaged(val pagination: PagedList<PokemonSearchInfo>) : SearchState()
        class LoadError(val error: Exception) : SearchState()
    }

    sealed class SearchEvent : UIEvent() {
        class ShowProfile(val pokemonUrl: String) : SearchEvent()
        class Message(@StringRes message: Int) : SearchEvent()
    }
}