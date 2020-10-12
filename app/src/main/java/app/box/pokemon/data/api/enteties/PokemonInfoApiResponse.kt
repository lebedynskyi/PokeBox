package app.box.pokemon.data.api.enteties

data class PokemonInfoApiResponse(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Long,
    val types: List<PokemonTypeApiResponse>,
    val sprites: PokemonSprites
)

data class PokemonTypeApiResponse(
    val slot: Int,
    val type: ValuePairApiAnswer
)

data class PokemonSprites(
    val back_default: String?,
    val back_female: String?,
    val back_shiny: String?,
    val back_shiny_female: String?,
    val front_default: String?,
    val front_female: String?,
    val front_shiny: String?,
    val front_shiny_female: String?
)