package app.box.pokemon.data.api

import app.box.pokemon.data.enteties.ApiPaginationAnswer
import app.box.pokemon.data.enteties.PokemonSearchInfo
import app.box.pokemon.data.enteties.PokemonInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val DEFAULT_PAGINATION_LIMIT = 20
const val DEFAULT_PAGINATION_OFFSET = 0

interface PokemonApiClient {
    @GET("pokemon/")
    suspend fun top(
        @Query("limit") limit: Int = DEFAULT_PAGINATION_LIMIT,
        @Query("offset") offset: Int = DEFAULT_PAGINATION_OFFSET
    ): ApiPaginationAnswer<PokemonSearchInfo>

    @GET("pokemon/{id}")
    suspend fun pokemonById(@Path(value = "id", encoded = true) id: String): PokemonInfo
}

