package app.box.pokemon.ui.profile

import android.util.Log
import app.box.pokemon.core.BaseViewModel
import app.box.pokemon.data.Repository

class ProfileViewModel(
    repository: Repository
) : BaseViewModel(repository) {
    fun loadProfile(url: String) {
        Log.d("Profile", "Load profile for URl $url")
    }
}