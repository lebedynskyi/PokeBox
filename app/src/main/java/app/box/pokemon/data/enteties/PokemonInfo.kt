package app.box.pokemon.data.enteties

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

@Entity(tableName = "PokemonInfo")
@TypeConverters(PokemonTypeConverter::class)
data class PokemonInfo(
    @PrimaryKey
    val id: Int,
    var name: String,
    val height: Int,
    val weight: Long,
    val types: List<PokemonType>,
    val imageUrl: String?
)

data class PokemonType(
    val slot: Int,
    val type: ValuePair
)

class PokemonTypeConverter {
    val moshi = Moshi.Builder().build()

    val pokemonListTypeAdapter: JsonAdapter<List<PokemonType>> = moshi.adapter(
        Types.newParameterizedType(
            MutableList::class.java,
            PokemonType::class.java
        )
    )

    @TypeConverter
    fun convertListTo(types: List<PokemonType>): String {
        return pokemonListTypeAdapter.toJson(types)
    }

    @TypeConverter
    fun convertStringTo(json: String): List<PokemonType>? {
        return pokemonListTypeAdapter.fromJson(json)
    }
}