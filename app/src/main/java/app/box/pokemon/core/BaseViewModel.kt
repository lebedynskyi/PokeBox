package app.box.pokemon.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.box.pokemon.data.Repository

open class BaseViewModel(
    protected val repository: Repository
) : ViewModel() {
    private val _progressLiveData: MutableLiveData<Boolean> = MutableLiveData(false)
    val progressLiveData: LiveData<Boolean>
        get() = _progressLiveData

    protected fun showProgress() {
        _progressLiveData.postValue(true)
    }

    protected fun hideProgress() {
        _progressLiveData.postValue(false)
    }
}