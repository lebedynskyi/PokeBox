package app.box.pokemon.core.di

import app.box.pokemon.data.Repository
import app.box.pokemon.data.RepositoryImpl
import org.koin.dsl.bind
import org.koin.dsl.module

val dataModule = module {
    single { RepositoryImpl() } bind Repository::class
}