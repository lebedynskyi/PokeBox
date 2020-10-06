package app.box.pokemon.data.api

import app.box.pokemon.data.enteties.ApiPaginationAnswer
import app.box.pokemon.data.enteties.PokemonSearchInfo
import app.box.pokemon.data.enteties.PokemonInfo
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApiClient {
    @GET("pokemon/")
    suspend fun top(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): ApiPaginationAnswer<PokemonSearchInfo>

    @GET("pokemon/{id}")
    suspend fun pokemonById(@Path(value = "id", encoded = true) id: String): PokemonInfo
}

