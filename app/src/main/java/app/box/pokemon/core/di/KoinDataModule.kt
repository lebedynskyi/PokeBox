package app.box.pokemon.core.di

import app.box.pokemon.BuildConfig
import app.box.pokemon.data.Repository
import app.box.pokemon.data.RepositoryImpl
import app.box.pokemon.data.api.PokemonApiClient
import app.box.pokemon.data.source.ApiDataSource
import okhttp3.OkHttpClient
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

val okHttpClient = OkHttpClient.Builder()
    .readTimeout(15, TimeUnit.SECONDS)
    .connectTimeout(15, TimeUnit.SECONDS)
    .build()

val pokemonRetrofit = Retrofit.Builder()
    .baseUrl(BuildConfig.POKEMON_API_URL)
    .addConverterFactory(MoshiConverterFactory.create())
    .client(okHttpClient)
    .build()

val dataModule = module {
    single { pokemonRetrofit.create(PokemonApiClient::class.java) }
    single { ApiDataSource(get()) }
    single { RepositoryImpl(get()) } bind Repository::class
}