package app.box.pokemon.data.api

import app.box.pokemon.data.api.enteties.PaginationApiAnswer
import app.box.pokemon.data.api.enteties.PokemonInfoApiResponse
import app.box.pokemon.data.api.enteties.PokemonSearchApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApiClient {
    @GET("pokemon/")
    suspend fun topPokemons(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): PaginationApiAnswer<PokemonSearchApiResponse>

    @GET("pokemon/{id}")
    suspend fun pokemonById(@Path(value = "id", encoded = true) id: String): PokemonInfoApiResponse
}

