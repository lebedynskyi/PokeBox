package app.box.pokemon.ui.search

import androidx.annotation.StringRes
import androidx.paging.PagingData
import app.box.pokemon.core.BaseViewModel
import app.box.pokemon.data.Repository
import app.box.pokemon.data.enteties.PokemonSearchInfo
import io.uniflow.core.flow.data.UIEvent
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

class SearchViewModel(
    repository: Repository
) : BaseViewModel(repository) {
    private val clearListChannel = Channel<Unit>(Channel.CONFLATED)

    @FlowPreview
    val topPokemons = flowOf(
        clearListChannel.receiveAsFlow().map { PagingData.empty() },
        repository.getTopPokemonsPaged()
    ).flattenMerge(2)

    fun onItemSelected(item: PokemonSearchInfo) = action {
        sendEvent(SearchEvent.ShowProfile(item.id))
    }

    sealed class SearchEvent : UIEvent() {
        class ShowProfile(val id: Int) : SearchEvent()
        class Message(@StringRes message: Int) : SearchEvent()
    }
}