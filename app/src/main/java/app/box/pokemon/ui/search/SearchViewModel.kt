package app.box.pokemon.ui.search

import androidx.annotation.StringRes
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import app.box.pokemon.core.BaseViewModel
import app.box.pokemon.data.Repository
import app.box.pokemon.data.enteties.PokemonSearchInfo
import io.uniflow.core.flow.data.UIEvent
import io.uniflow.core.flow.data.UIState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

const val SEARCH_DELAY: Long = 500

@ExperimentalCoroutinesApi
@FlowPreview
class SearchViewModel(
    repository: Repository
) : BaseViewModel(repository) {
    private val searchQueriesChanel = Channel<String?>(Channel.UNLIMITED)
    private var latestSearchQuery : String? = null

    init {
        viewModelScope.launch {
            searchQueriesChanel.receiveAsFlow()
                .debounce(SEARCH_DELAY)
                .flatMapLatest {
                    if (it.isNullOrBlank()) {
                        repository.getTopPokemonsPaged()
                    } else {
                        repository.searchCachedPokemon(it)
                    }
                }
                .collect { result ->
                    action {
                        setState { SearchViewState.ShowList(result) }
                    }
                }
        }
    }

    fun loadTopPokemons() {
        searchQueriesChanel.offer(null)
    }

    fun onItemSelected(item: PokemonSearchInfo) = action {
        sendEvent(SearchEvent.ShowProfile(item.id))
    }

    fun onSearchPerformed(newText: String?) {
        searchQueriesChanel.offer(newText)
    }

    sealed class SearchViewState : UIState() {
        class ShowList(val pokemonList: PagingData<PokemonSearchInfo>) : SearchViewState()
    }

    sealed class SearchEvent : UIEvent() {
        class ShowProfile(val id: Int) : SearchEvent()
        class Message(@StringRes message: Int) : SearchEvent()
    }
}