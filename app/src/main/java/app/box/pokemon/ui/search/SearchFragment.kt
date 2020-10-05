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
import io.uniflow.androidx.flow.onEvents
import io.uniflow.androidx.flow.onStates
import io.uniflow.core.flow.data.UIEvent
import kotlinx.android.synthetic.main.fragment_search.*
import org.koin.android.viewmodel.ext.android.viewModel

class SearchFragment : BaseFragment(R.layout.fragment_search) {
    private val searchAdapter = SearchAdapter(::onSearchItemClicked)
    private val fragmentViewModel: SearchViewModel by viewModel()

    private lateinit var fragmentBinding: FragmentSearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentViewModel.loadFavoritesPokemon()
        setHasOptionsMenu(true)
        onStates(fragmentViewModel) {
            when (it) {
                is SearchViewModel.SearchState.ResultState -> displayResultState(it)
                is SearchViewModel.SearchState.LoadError -> displayErrorState()
            }
        }

        onEvents(fragmentViewModel) {
            val event = it.take()
            when (event) {
                is UIEvent.Loading -> handleProgressEvent()
                is SearchViewModel.SearchEvent.ShowProfile -> handleShowProfileEvent(event)
            }
        }
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

    private fun handleProgressEvent() {
        fragmentBinding.searchProgress.isRefreshing = true
    }

    private fun handleShowProfileEvent(event: SearchViewModel.SearchEvent.ShowProfile) {
        val action = SearchFragmentDirections.navigateToProfile(event.pokemonUrl)
        navigation.navigate(action)
    }

    private fun displayResultState(resultState: SearchViewModel.SearchState.ResultState) {
        fragmentBinding.searchProgress.isRefreshing = false
        fragmentBinding.searchProgress
        searchAdapter.submitList(resultState.resultList)
    }

    private fun displayErrorState() {

    }

    private fun onSearchItemClicked(item: SearchItem) {
        fragmentViewModel.onItemSelected(item)
    }
}