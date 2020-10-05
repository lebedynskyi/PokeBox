package app.box.pokemon.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import app.box.pokemon.R
import app.box.pokemon.core.adapter.AdapterDiffItemCallback
import app.box.pokemon.databinding.ItemSearchBinding

class SearchAdapter(val itemListener: (SearchItem) -> Unit) :
    ListAdapter<SearchItem, SearchAdapter.SearchHolder>(AdapterDiffItemCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search, parent, false)
        return SearchHolder(view)
    }

    override fun onBindViewHolder(holder: SearchHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bindItem(item)
        } else {
            holder.clear()
        }
    }

    inner class SearchHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemBinding = ItemSearchBinding.bind(itemView)

        fun bindItem(item: SearchItem) {
            itemBinding.item = item
            itemBinding.root.setOnClickListener { itemListener.invoke(item) }
        }

        fun clear() {
            itemBinding.item = null
        }
    }
}