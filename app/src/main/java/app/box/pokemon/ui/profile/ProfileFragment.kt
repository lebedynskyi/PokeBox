package app.box.pokemon.ui.profile

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import app.box.pokemon.R
import app.box.pokemon.core.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment(R.layout.fragment_layout) {
    private val profileArgs: ProfileFragmentArgs by navArgs()
    private val profileViewModel: ProfileViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        profileViewModel.loadProfile(profileArgs.url)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    }
}