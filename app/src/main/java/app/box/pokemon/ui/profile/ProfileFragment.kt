package app.box.pokemon.ui.profile

import android.os.Bundle
import android.view.View
import app.box.pokemon.R
import app.box.pokemon.core.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment(R.layout.fragment_layout) {
    val profileViewModel: ProfileViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        profileViewModel.loadProfile()
    }
}