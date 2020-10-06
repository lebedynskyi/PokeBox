package app.box.pokemon.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import app.box.pokemon.R
import app.box.pokemon.data.enteties.PokemonSearchInfo
import app.box.pokemon.databinding.ItemSearchBinding

class SearchAdapter(val itemListener: (PokemonSearchInfo) -> Unit) :
    PagingDataAdapter<PokemonSearchInfo, SearchAdapter.SearchHolder>(searchDiffCalback) {
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

        fun bindItem(item: PokemonSearchInfo) {
            itemBinding.item = item
            itemBinding.root.setOnClickListener { itemListener.invoke(item) }
        }

        fun clear() {
            itemBinding.item = null
        }
    }
}

object searchDiffCalback : DiffUtil.ItemCallback<PokemonSearchInfo>() {
    override fun areItemsTheSame(oldItem: PokemonSearchInfo, newItem: PokemonSearchInfo): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: PokemonSearchInfo, newItem: PokemonSearchInfo
    ): Boolean {
        return oldItem.equals(newItem)
    }
}