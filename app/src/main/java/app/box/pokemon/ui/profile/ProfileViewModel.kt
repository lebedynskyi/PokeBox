package app.box.pokemon.ui.profile

import app.box.pokemon.core.BaseViewModel
import app.box.pokemon.data.Repository
import app.box.pokemon.data.enteties.PokemonInfo
import io.uniflow.core.flow.data.UIEvent
import io.uniflow.core.flow.data.UIState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNotNull

class ProfileViewModel(
    repository: Repository
) : BaseViewModel(repository) {

    fun loadProfile(pokemonId: Int) = action {
        sendEvent(UIEvent.Loading)
        repository.getPokemonById(pokemonId).filterNotNull()
            .collect { pokemon ->
                setState { ProfileState.ProfileLoaded(pokemon) }
            }
    }

    sealed class ProfileState : UIState() {
        class ProfileLoaded(val profileItem: PokemonInfo) : ProfileState()
        object NotFound : ProfileState()
    }
}