package app.box.pokemon.core

import android.app.Application
import androidx.paging.ExperimentalPagingApi
import app.box.pokemon.core.di.dataModule
import app.box.pokemon.core.di.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PokemonApp : Application() {
    @ExperimentalPagingApi
    override fun onCreate() {
        super.onCreate()

        // Initialize DI Framework
        startKoin {
            androidContext(this@PokemonApp)
            modules(viewModelsModule, dataModule)
        }
    }
}