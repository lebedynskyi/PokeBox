package app.box.pokemon.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.box.pokemon.data.Repository

open class BaseViewModel(
    protected val repository: Repository
) : ViewModel() {
    val _progressLiveData: MutableLiveData<Boolean> = MutableLiveData(false)
    val progressLiveData: LiveData<Boolean>
        get() = _progressLiveData

}