package app.box.pokemon.data.api.enteties

data class PaginationApiAnswer<T>(
    val count: Int,
    val next: String,
    val previous: String,
    val results: List<T>
)

data class ValuePairApiAnswer(
    val url: String,
    val name: String
)