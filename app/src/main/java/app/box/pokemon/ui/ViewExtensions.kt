package app.box.pokemon.ui

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import app.box.pokemon.core.adapter.AdapterItem

@BindingAdapter("app:items")
fun RecyclerView.setItems(items: List<AdapterItem>?) {
    val listAdapter = adapter as ListAdapter<AdapterItem, *>
    listAdapter.submitList(items.orEmpty())
}