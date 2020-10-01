package app.box.pokemon.core.adapter

import androidx.recyclerview.widget.DiffUtil

class AdapterDiffItemCallback<T> : DiffUtil.ItemCallback<T>()
        where T : AdapterItem {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.equals(newItem)
    }
}