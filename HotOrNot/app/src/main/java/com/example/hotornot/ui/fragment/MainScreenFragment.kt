package com.example.hotornot.ui.fragment

import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import com.example.hotornot.R
import com.example.hotornot.data.model.Friend
import com.example.hotornot.databinding.FragmentMainScreenBinding
import com.example.hotornot.ui.MainActivity
import com.example.hotornot.viewModel.MainScreenFragmentViewModel
import com.google.android.material.chip.Chip

class MainScreenFragment : BaseFragment() {

    private val viewModel: MainScreenFragmentViewModel by viewModels()
    private lateinit var binding: FragmentMainScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMainScreenBinding.inflate(inflater, container, false)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setNavigationMenu()
        observeData()
        navigateToNextScreen()
    }

    override fun onResume() {
        super.onResume()
        viewModel.onViewResumed()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)
    }

    private fun observeData() {
        viewModel.fetchUiModel.observe(viewLifecycleOwner) {
            binding.uiModel = it
            onRandomFriendLoaded(it.friend)
        }
        viewModel.navigateLiveData.observe(viewLifecycleOwner) {
            openScreen(it)
        }
    }

    private fun onRandomFriendLoaded(friend: Friend?) {
        if (friend != null)
            setFriendCharacteristics(friend.characteristics)
    }

    private fun setNavigationMenu() {
        val toolbar = (activity as MainActivity?)?.toolbar
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                toolbar?.overflowIcon =
                    ContextCompat.getDrawable(requireContext(), R.drawable.ic_three_dots)
                menuInflater.inflate(R.menu.toolbar_menu, toolbar?.menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                onSortedOptionsItemSelected(menuItem)
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun onSortedOptionsItemSelected(menuItem: MenuItem) =
        when (menuItem.itemId) {
            R.id.profileScreen -> {
                viewModel.navigateToProfileScreen()
                true
            }
            R.id.logOut -> {
                viewModel.clearPref()
                activity?.finish()
                true
            }
            else -> false
        }

    private fun setFriendCharacteristics(characteristics: List<String>) {
        binding.chipGroup.removeAllViews()
        for (characteristic in characteristics) {
            val chip = Chip(view?.context)
            chip.text = characteristic
            binding.chipGroup.addView(chip)
        }
    }

    private fun navigateToNextScreen() {
        if (!isNetworkAvailable(requireContext())) {
            openScreen(MainScreenFragmentDirections.actionMainScreenFragmentToNoNetworkConnectionScreen())
        }
    }
}