package app.box.pokemon.data.api

import app.box.pokemon.data.model.ApiPaginationAnswer
import app.box.pokemon.data.model.ApiNamedResource
import app.box.pokemon.data.model.PokemonInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val DEFAULT_PAGINATION_LIMIT = 20
const val DEFAULT_PAGINATION_OFFSET = 0

interface PokemonApiClient {
    @GET("pokemon/")
    fun top(
        @Query("limit") limit: Int = DEFAULT_PAGINATION_LIMIT,
        @Query("offset") offset: Int = DEFAULT_PAGINATION_OFFSET
    ): Call<ApiPaginationAnswer<ApiNamedResource>>

    @GET("pokemon/{id}")
    fun pokemonById(@Path(value = "id", encoded = true) id: String): Call<PokemonInfo>
}

