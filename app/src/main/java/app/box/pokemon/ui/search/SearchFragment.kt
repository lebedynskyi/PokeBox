package app.box.pokemon.ui.search

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import app.box.pokemon.R
import app.box.pokemon.core.BaseFragment
import app.box.pokemon.data.enteties.PokemonSearchInfo
import app.box.pokemon.databinding.FragmentSearchBinding
import io.uniflow.androidx.flow.onEvents
import io.uniflow.core.flow.data.UIEvent
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import org.koin.android.viewmodel.ext.android.viewModel

@FlowPreview
class SearchFragment : BaseFragment(R.layout.fragment_search) {
    private val searchAdapter = SearchAdapter(::onSearchItemClicked)
    private val searchViewModel: SearchViewModel by viewModel()

    private lateinit var fragmentBinding: FragmentSearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        onEvents(searchViewModel) {
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
            searchViewModel.topPokemons.collectLatest {
                searchAdapter.submitData(it)
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
}