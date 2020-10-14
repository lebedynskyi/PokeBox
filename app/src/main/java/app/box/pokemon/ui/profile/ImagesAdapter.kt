package app.box.pokemon.ui.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import app.box.pokemon.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_image.view.*

class ImagesAdapter : RecyclerView.Adapter<ImagesAdapter.VesselPhotoHolder>() {
    private val dataList: ArrayList<String> = ArrayList()

    fun setItems(items: List<String>) {
        dataList.clear()
        dataList.addAll(items)
        notifyDataSetChanged()
    }

    override fun getItemCount() = dataList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VesselPhotoHolder {
        return VesselPhotoHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        )
    }

    override fun onBindViewHolder(holder: VesselPhotoHolder, position: Int) {
        holder.bindPhoto(dataList[position])
    }

    class VesselPhotoHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindPhoto(photo: String) {
            itemView.image.scaleType =
                if (absoluteAdapterPosition == 0) ImageView.ScaleType.CENTER_INSIDE else ImageView.ScaleType.FIT_CENTER

            Glide
                .with(itemView.context)
                .load(photo)
                .into(itemView.image)
        }
    }
}