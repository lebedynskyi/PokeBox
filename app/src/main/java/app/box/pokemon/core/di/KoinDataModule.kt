package app.box.pokemon.core.di

import app.box.pokemon.BuildConfig
import app.box.pokemon.data.Repository
import app.box.pokemon.data.RepositoryImpl
import app.box.pokemon.data.api.PokemonApiClient
import app.box.pokemon.data.source.ApiDataSource
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val pokemonRetrofit = Retrofit.Builder()
    .baseUrl(BuildConfig.POKEMON_API_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val dataModule = module {
    single { pokemonRetrofit.create(PokemonApiClient::class.java) }
    single { ApiDataSource(get())}
    single { RepositoryImpl(get()) } bind Repository::class
}