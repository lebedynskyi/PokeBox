package app.box.pokemon.core


import app.box.pokemon.data.Repository
import io.uniflow.androidx.flow.AndroidDataFlow
import io.uniflow.core.flow.data.UIState

open class BaseViewModel(
    protected val repository: Repository
) : AndroidDataFlow(defaultState = UIState.Empty) {

}