package app.box.pokemon.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.box.pokemon.data.enteties.PokemonInfo
import app.box.pokemon.data.enteties.PokemonSearchInfo
import kotlinx.coroutines.flow.Flow


@Dao
interface PokemonDao {
    // Pokemon Search
    @Query("SELECT * FROM Pokemons ORDER BY id COLLATE NOCASE ASC")
    fun getTopPokemonsPaging(): PagingSource<Int, PokemonSearchInfo>

    @Query("SELECT * FROM Pokemons WHERE name LIKE '%' || :query || '%' ORDER BY id COLLATE NOCASE ASC")
    fun searchPokemon(query: String): PagingSource<Int, PokemonSearchInfo>

    @Query("SELECT COUNT(url) FROM Pokemons")
    suspend fun getTopPokemonCount(): Int

    @Insert(entity = PokemonSearchInfo::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTopPokemons(pokemons: List<PokemonSearchInfo>)

    @Query("DELETE FROM pokemons")
    suspend fun clearPokemons()

    // Pokemon Details
    @Query("SELECT * FROM PokemonInfo WHERE id = :pokemonId")
    fun getPokemonInfo(pokemonId: Int): Flow<PokemonInfo>

    @Insert(entity = PokemonInfo::class, onConflict = OnConflictStrategy.REPLACE)
    fun savePokemonInfo(pokemon: PokemonInfo)
}
