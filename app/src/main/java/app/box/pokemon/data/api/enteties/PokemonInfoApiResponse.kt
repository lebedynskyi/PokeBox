package app.box.pokemon.data.api.enteties

data class PokemonInfoApiResponse(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Long,
    val types: List<PokemonTypeApiResponse>,
)

data class PokemonTypeApiResponse(
    val slot: Int,
    val type: ValuePairApiAnswer
)