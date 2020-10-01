package app.box.pokemon.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import app.box.pokemon.core.BaseViewModel
import app.box.pokemon.data.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class SearchViewModel(
    repository: Repository
) : BaseViewModel(repository) {
    private val _searchLiveData = MutableLiveData<List<SearchItem>>()
    val searchLiveData: LiveData<List<SearchItem>>
        get() = _searchLiveData

    fun loadFavoritesPokemons() {
        viewModelScope.launch {
            showProgress()
            val top = loadFavoritesPokemonsAsync()
            _searchLiveData.value = top
            hideProgress()
        }
    }

    private suspend fun loadFavoritesPokemonsAsync() = withContext(Dispatchers.IO) {
        return@withContext repository.getTopPokemons().results.map {
            SearchItem(it.name.capitalize(Locale.getDefault()), it.url)
        }
    }
}