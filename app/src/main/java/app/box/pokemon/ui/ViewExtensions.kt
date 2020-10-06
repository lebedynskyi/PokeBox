package app.box.pokemon.ui

import android.text.TextUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import app.box.pokemon.R
import app.box.pokemon.data.enteties.PokemonType
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

@BindingAdapter("pokemonTypes")
fun TextView.setPokemonTypes(types: List<PokemonType>?) {
    this.text = TextUtils.join(", ", types.orEmpty().map { it.type.name })
}

fun Fragment.showSnackBar(message: String?) {
    Snackbar.make(this.requireView(), message.orEmpty(), Snackbar.LENGTH_LONG).show()
}