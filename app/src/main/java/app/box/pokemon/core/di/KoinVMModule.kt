package app.box.pokemon.core.di

import app.box.pokemon.ui.profile.ProfileViewModel
import app.box.pokemon.ui.search.SearchViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel { SearchViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
}