package app.box.pokemon.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.box.pokemon.data.enteties.PokemonInfo
import app.box.pokemon.data.enteties.PokemonSearchInfo


@Dao
interface PokemonDao {
    @Query("SELECT * FROM Pokemons ORDER BY url ASC")
    fun getTopPokemons(): List<PokemonSearchInfo>

    @Insert(entity = PokemonSearchInfo::class, onConflict = OnConflictStrategy.REPLACE)
    fun saveTopPokemons(pokemons: List<PokemonSearchInfo>)

    @Query("SELECT * FROM PokemonInfo WHERE id = :pokemonId")
    fun getPokemonInfo(pokemonId: String): PokemonInfo

    @Insert(entity = PokemonInfo::class, onConflict = OnConflictStrategy.REPLACE)
    fun savePokemonInfo(pokemon: PokemonInfo)
}
