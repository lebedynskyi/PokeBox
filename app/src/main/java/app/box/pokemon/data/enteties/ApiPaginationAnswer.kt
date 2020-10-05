package app.box.pokemon.data.enteties

data class ApiPaginationAnswer<T>(
    val count: Int,
    val next: String,
    val previous: String,
    val results: List<T>
)