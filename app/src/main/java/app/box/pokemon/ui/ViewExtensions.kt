package app.box.pokemon.ui

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import app.box.pokemon.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.snackbar.Snackbar

@BindingAdapter("urlImage")
fun ImageView.loadImage(imageUrl: String?) {
    Glide.with(this)
        .load(imageUrl)
        .placeholder(R.drawable.ic_placeholder)
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .into(this)
}

fun Fragment.showSnackBar(message: String?) {
    Snackbar.make(this.requireView(), message.orEmpty(), Snackbar.LENGTH_LONG).show()
}