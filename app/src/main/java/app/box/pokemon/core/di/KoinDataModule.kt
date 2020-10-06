package app.box.pokemon.core.di

import androidx.core.content.ContextCompat
import androidx.room.Room
import app.box.pokemon.BuildConfig
import app.box.pokemon.data.Repository
import app.box.pokemon.data.RepositoryImpl
import app.box.pokemon.data.api.PokemonApiClient
import app.box.pokemon.data.db.PokemonDatabase
import app.box.pokemon.data.source.ApiDataSource
import app.box.pokemon.data.source.DBDataSource
import app.box.pokemon.data.source.NetworkStateDataSource
import okhttp3.OkHttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.experimental.builder.single
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.Executor
import java.util.concurrent.Executors
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
    // Misc
    single { NetworkStateDataSource(get()) }
    // APi
    single { pokemonRetrofit.create(PokemonApiClient::class.java) }
    single { ApiDataSource(get()) }
    // DB
    single { Room.databaseBuilder(get(), PokemonDatabase::class.java, "PokemonDB").build() }
    single { DBDataSource(get()) }

    //
    single (qualifier = named("Background"), definition = {Executors.newSingleThreadExecutor()}) bind Executor::class
    single (qualifier = named("Main"), definition = {ContextCompat.getMainExecutor(get())}) bind Executor::class
    single { RepositoryImpl(get(), get(), get(), get(named("Background")), get(named("Main"))) } bind Repository::class
}