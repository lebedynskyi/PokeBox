package app.box.pokemon.data.model

data class ApiPaginationAnswer<T>(
    val count: Int,
    val next: String,
    val previous: String,
    val results: Array<T>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ApiPaginationAnswer<*>

        if (count != other.count) return false
        if (next != other.next) return false
        if (previous != other.previous) return false
        if (!results.contentEquals(other.results)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = count
        result = 31 * result + next.hashCode()
        result = 31 * result + previous.hashCode()
        result = 31 * result + results.contentHashCode()
        return result
    }
}