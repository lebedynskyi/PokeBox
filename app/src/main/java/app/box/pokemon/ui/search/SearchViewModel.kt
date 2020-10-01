package app.box.pokemon.ui.search

import androidx.lifecycle.viewModelScope
import app.box.pokemon.core.BaseViewModel
import app.box.pokemon.data.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchViewModel(
    repository: Repository
) : BaseViewModel(repository) {

    fun loadFavoritesPokemons() {
        viewModelScope.launch {
            showProgress()
            val top = loadFavoritesPokemonsAsync()
            hideProgress()
        }
    }

    private suspend fun loadFavoritesPokemonsAsync() = withContext(Dispatchers.IO) {
        return@withContext repository.getTopPokemons()
    }
}