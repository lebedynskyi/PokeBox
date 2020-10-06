package app.box.pokemon.data.enteties

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Pokemons")
data class PokemonSearchInfo(
    @PrimaryKey
    val id: String,
    val url: String,
    val name: String,
    val imageUrl: String?
)