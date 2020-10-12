package app.box.pokemon.ui.search

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import app.box.pokemon.R
import app.box.pokemon.core.BaseFragment
import app.box.pokemon.data.enteties.PokemonSearchInfo
import app.box.pokemon.databinding.FragmentSearchBinding
import io.uniflow.androidx.flow.onEvents
import io.uniflow.androidx.flow.onStates
import io.uniflow.core.flow.data.UIEvent
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

@FlowPreview
@ExperimentalCoroutinesApi
class SearchFragment : BaseFragment(R.layout.fragment_search) {
    private val searchAdapter = SearchAdapter(::onSearchItemClicked)
    private val searchViewModel: SearchViewModel by viewModel()

    private lateinit var fragmentBinding: FragmentSearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        searchViewModel.loadTopPokemons()

        onStates(searchViewModel) {
            when (it) {
                is SearchViewModel.SearchViewState.ShowList -> lifecycleScope.launch {
                    searchAdapter.submitData(it.pokemonList)
                }
            }
        }

        onEvents(searchViewModel) {
            when (val event = it.take()) {
                is UIEvent.Loading -> handleProgressEvent()
                is SearchViewModel.SearchEvent.ShowProfile -> handleShowProfileEvent(event)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_menu, menu)

        val searchItem = menu.findItem(R.id.menu_search)
        val searchView = searchItem.actionView as SearchView
        searchView.queryHint = getString(R.string.search_pokmeon)
        searchView.setOnQueryTextListener(toolbarSearchListener)

        searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean { return true }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                searchViewModel.loadTopPokemons()
               return true
            }
        })

        val txtSearch =
            searchView.findViewById<View>(androidx.appcompat.R.id.search_src_text) as EditText
        txtSearch.setHintTextColor(Color.LTGRAY)
        txtSearch.setTextColor(Color.WHITE)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fragmentBinding = FragmentSearchBinding.bind(view).apply {
            this.lifecycleOwner = viewLifecycleOwner
            this.adapter = searchAdapter
            this.viewModel = searchViewModel
        }
        fragmentBinding.searchRecycler.itemAnimator = null

        fragmentBinding.searchRecycler.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                RecyclerView.VERTICAL
            )
        )

        parentActivity.setSupportActionBar(search_toolbar)
        fragmentBinding.searchProgress.setOnRefreshListener { searchAdapter.refresh() }

        lifecycleScope.launchWhenCreated {
            searchAdapter.loadStateFlow.collectLatest { loadStates ->
                fragmentBinding.searchProgress.isRefreshing =
                    loadStates.refresh is LoadState.Loading
            }
        }

        lifecycleScope.launchWhenCreated {
            searchAdapter.loadStateFlow
                // Only emit when REFRESH LoadState for RemoteMediator changes.
                .distinctUntilChangedBy { it.refresh }
                // Only react to cases where Remote REFRESH completes i.e., NotLoading.
                .filter { it.refresh is LoadState.NotLoading }
                .collect { fragmentBinding.searchRecycler.scrollToPosition(0) }
        }
    }

    private fun handleProgressEvent() {
        fragmentBinding.searchProgress.isRefreshing = true
    }

    private fun handleShowProfileEvent(event: SearchViewModel.SearchEvent.ShowProfile) {
        val action = SearchFragmentDirections.navigateToProfile(event.id)
        navigation.navigate(action)
    }

    private fun onSearchItemClicked(item: PokemonSearchInfo) {
        searchViewModel.onItemSelected(item)
    }

    private val toolbarSearchListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextChange(newText: String?): Boolean {
            searchViewModel.onSearchPerformed(newText)
            return true
        }

        override fun onQueryTextSubmit(query: String?): Boolean {
            searchViewModel.onSearchPerformed(query)
            return false
        }
    }
}