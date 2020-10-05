package app.box.pokemon.ui.search

import app.box.pokemon.core.adapter.AdapterItem

data class SearchItem(
    val name: String,
    val url: String,
    val imageUrl: String?
) : AdapterItem()