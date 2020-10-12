package app.box.pokemon.ui.profile

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import app.box.pokemon.R
import app.box.pokemon.core.BaseFragment
import app.box.pokemon.databinding.FragmentProfileBinding
import com.google.android.material.tabs.TabLayoutMediator
import io.uniflow.androidx.flow.onEvents
import io.uniflow.androidx.flow.onStates
import io.uniflow.core.flow.data.UIEvent
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.android.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment(R.layout.fragment_profile) {
    private val profileArgs: ProfileFragmentArgs by navArgs()
    private val profileViewModel: ProfileViewModel by viewModel()
    private val imagesAdapter = ImagesAdapter()

    private lateinit var fragmentBinding: FragmentProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        profileViewModel.loadProfile(profileArgs.id)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fragmentBinding = FragmentProfileBinding.bind(view).apply {
            this.lifecycleOwner = viewLifecycleOwner
            this.images.adapter = imagesAdapter
        }
        onStates(profileViewModel) {
            when (it) {
                is ProfileViewModel.ProfileState.ProfileLoaded -> displayProfileState(it)
            }
        }

        onEvents(profileViewModel) {
            when (it.take()) {
                is UIEvent.Loading -> showLoading()
            }
        }
    }

    private fun displayProfileState(profileState: ProfileViewModel.ProfileState.ProfileLoaded) {
        val profile = profileState.profileItem
        fragmentBinding.profileItem = profile
        imagesAdapter.setItems(profile.sprites.filterNotNull().toMutableList().also { list ->
            profile.imageUrl?.let {
                list.add(0, it)
            }
        })
        TabLayoutMediator(images_indicator, images) { _, _ -> }.attach()
    }

    private fun showLoading() {

    }
}