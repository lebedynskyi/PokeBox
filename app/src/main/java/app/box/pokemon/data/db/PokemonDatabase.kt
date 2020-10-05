package app.box.pokemon.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import app.box.pokemon.data.enteties.PokemonInfo
import app.box.pokemon.data.enteties.PokemonSearchInfo

@Database(
    entities = [PokemonSearchInfo::class, PokemonInfo::class],
    exportSchema = false,
    version = 1
)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
}