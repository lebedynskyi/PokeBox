package app.box.pokemon.ui.profile

import android.text.TextUtils
import app.box.pokemon.core.BaseViewModel
import app.box.pokemon.data.Repository
import io.uniflow.core.flow.data.UIEvent
import io.uniflow.core.flow.data.UIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class ProfileViewModel(
    repository: Repository
) : BaseViewModel(repository) {
    fun loadProfile(pokemonId: String) = action(
        onAction = {
            sendEvent(UIEvent.Loading)
            val profile = getProfileAsync(pokemonId)
            if (profile != null) {
                setState(ProfileState.ProfileLoaded(profile))
            } else {
                setState(ProfileState.NotFound)
            }
        },
        onError = { _, _ ->

        }
    )

    private suspend fun getProfileAsync(pokemonId: String) = withContext(Dispatchers.IO) {
        val profile = repository.getPokemonById(pokemonId)
        return@withContext profile?.let { info ->
            ProfileItem(
                info.id,
                info.name.capitalize(Locale.getDefault()),
                info.height,
                info.weight,
                TextUtils.join(", ", info.types.map { it.type.name.capitalize(Locale.getDefault()) }),
                info.imageUrl
            )
        }
    }

    sealed class ProfileState : UIState() {
        class ProfileLoaded(val profileItem: ProfileItem) : ProfileState()
        object NotFound : ProfileState()
    }
}