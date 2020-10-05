package app.box.pokemon.ui

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import app.box.pokemon.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

@BindingAdapter("app:urlImage")
fun ImageView.loadImage(imageUrl: String?) {
    Glide.with(this)
        .load(imageUrl)
        .placeholder(R.drawable.ic_placeholder)
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .into(this)
}