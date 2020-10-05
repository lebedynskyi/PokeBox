package app.box.pokemon.data.enteties

data class PokemonInfo(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Long,
    val types: Array<PokemonType>,
    val sprites: PokemonSprite
)

data class PokemonType(
    val slot: Int,
    val type: ApiNamedResource
)

data class PokemonSprite(
    val back_default: String,
    val back_female: String,
    val back_shiny: String,
    val back_shiny_female: String,
    val front_default: String,
    val front_female: String,
    val front_shiny: String,
    val front_shiny_female: String
)