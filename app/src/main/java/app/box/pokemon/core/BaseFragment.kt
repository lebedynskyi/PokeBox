package app.box.pokemon.core

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController

open class BaseFragment(
    @LayoutRes layoutId: Int
) : Fragment(layoutId) {
    protected val navigation: NavController by lazy { findNavController() }
    protected val parentActivity: MainActivity by lazy { requireActivity() as MainActivity }
}