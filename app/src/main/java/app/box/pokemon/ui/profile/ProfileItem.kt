package app.box.pokemon.ui.profile

data class ProfileItem(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Long,
    val type: String,
    val imageUrl: String?
)