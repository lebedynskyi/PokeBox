package app.box.pokemon.ui.profile

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import app.box.pokemon.R
import app.box.pokemon.core.BaseFragment
import app.box.pokemon.databinding.FragmentProfileBinding
import io.uniflow.androidx.flow.onEvents
import io.uniflow.androidx.flow.onStates
import io.uniflow.core.flow.data.UIEvent
import org.koin.android.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment(R.layout.fragment_profile) {
    private val profileArgs: ProfileFragmentArgs by navArgs()
    private val profileViewModel: ProfileViewModel by viewModel()

    private lateinit var fragmentBinding: FragmentProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        profileViewModel.loadProfile(profileArgs.id)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fragmentBinding = FragmentProfileBinding.bind(view).apply {
            this.lifecycleOwner = viewLifecycleOwner
        }
        onStates(profileViewModel) {
            when (it) {
                is ProfileViewModel.ProfileState.ProfileLoaded -> displayProfileState(it)
                is ProfileViewModel.ProfileState.NotFound -> displayEmptyState(it)
            }
        }

        onEvents(profileViewModel) {
            when (it.take()) {
                is UIEvent.Loading -> showLoading()
            }
        }
    }

    private fun displayProfileState(profileState: ProfileViewModel.ProfileState.ProfileLoaded) {
        fragmentBinding.profileItem = profileState.profileItem
    }

    private fun displayEmptyState(uiState: ProfileViewModel.ProfileState.NotFound) {

    }

    private fun showLoading() {

    }
}