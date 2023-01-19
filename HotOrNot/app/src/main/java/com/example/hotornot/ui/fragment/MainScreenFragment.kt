package com.example.hotornot.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
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

private const val TYPE_SEND_EMAIL_INTENT = "text/plain"
private const val EMPTY_STRING = " "
private const val DATA_SEND_EMAIL_INTENT = "mailto"

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
        setEditButton()
        sendEmailConfirmClick()
//        onOptionsItemsSelected()
        observeData()
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

    private fun sendEmailConfirmClick() = binding.icSendEmail.setOnClickListener { sendEmail() }

    private fun onRandomFriendLoaded(friend: Friend?) {
        if (friend != null)
            setFriendCharacteristics(friend.characteristics)
    }

    private fun setEditButton() {
        val toolbar = (activity as MainActivity?)?.toolbar
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                toolbar?.overflowIcon =
                    ContextCompat.getDrawable(requireContext(), R.drawable.ic_three_dots)
                menuInflater.inflate(R.menu.toolbar_menu, toolbar?.menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
//                viewModel.onEditButtonClicked()
//                menuItem.isVisible = false
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

    private fun sendEmail() {
        val user = viewModel.getUser()
        val recipient = user?.email
        val text = getString(R.string.email_text)
        val messageIntent = Intent(Intent.ACTION_SENDTO)

        messageIntent.apply {
            data = Uri.parse(DATA_SEND_EMAIL_INTENT)
            type = (TYPE_SEND_EMAIL_INTENT)
            putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
            putExtra(Intent.EXTRA_TEXT, text + EMPTY_STRING + recipient)
        }
        try {
            startActivity(Intent.createChooser(messageIntent, getString(R.string.send_email)))
        } catch (e: Exception) {
            Toast.makeText(this.context, e.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun setFriendCharacteristics(characteristics: List<String>) {
        binding.chipGroup.removeAllViews()
        for (characteristic in characteristics) {
            val chip = Chip(view?.context)
            chip.text = characteristic
            binding.chipGroup.addView(chip)
        }
    }
}