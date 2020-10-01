package app.box.pokemon.ui.search

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import app.box.pokemon.R
import app.box.pokemon.core.BaseFragment
import app.box.pokemon.databinding.FragmentSearchBinding
import kotlinx.android.synthetic.main.fragment_search.*
import org.koin.android.viewmodel.ext.android.viewModel

class SearchFragment : BaseFragment(R.layout.fragment_search) {
    private val searchAdapter = SearchAdapter()
    private val fragmentViewModel: SearchViewModel by viewModel()
    lateinit var fragmentBinding: FragmentSearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentViewModel.loadFavoritesPokemons()
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_menu, menu)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fragmentBinding = FragmentSearchBinding.bind(view).apply {
            this.lifecycleOwner = viewLifecycleOwner
            this.adapter = searchAdapter
            this.viewModel = fragmentViewModel
        }

        fragmentBinding.searchRecycler.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                RecyclerView.VERTICAL
            )
        )

        parentActivity.setSupportActionBar(search_toolbar)
    }
}