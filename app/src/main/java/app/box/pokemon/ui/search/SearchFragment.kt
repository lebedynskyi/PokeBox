package app.box.pokemon.ui.search

import android.os.Bundle
import android.view.View
import app.box.pokemon.R
import app.box.pokemon.core.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel

class SearchFragment : BaseFragment(R.layout.fragment_search) {
    val myViewModel: SearchViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        myViewModel.loadFavoritesPokemons()
    }
}