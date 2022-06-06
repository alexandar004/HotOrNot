package com.example.hotornot.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.hotornot.R
import com.example.hotornot.data.local.PreferencesUtil
import com.example.hotornot.data.model.Friend
import com.example.hotornot.data.repository.FriendRepository
import com.example.hotornot.databinding.FragmentMainScreenBinding
import com.google.android.material.chip.Chip


private const val TYPE_SEND_EMAIL_INTENT = "text/plain"
private const val DATA_SEND_EMAIL_INTENT = "mailto"
private const val HOT_NAME = "Georgi"
private const val NOT_HOT_NAME = "Stan"

class MainScreenFragment : BaseFragment() {

    private lateinit var binding: FragmentMainScreenBinding
    private lateinit var preferencesUtil: PreferencesUtil
    private lateinit var friendRepository: FriendRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMainScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun goToNextScreen() =
        findNavController().navigate(R.id.action_mainScreenFragment_to_profileScreenFragment)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferencesUtil = PreferencesUtil.getInstance(view.context)
        friendRepository = FriendRepository(requireContext())
        clickButtonsHotOrNotListener()
        sendEmailClickListener()
        selectItemFromToolbar()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)
    }

    private fun selectItemFromToolbar() {
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.profileScreen -> {
                    goToNextScreen()
                    true
                }
                R.id.logOut -> {
                    preferencesUtil.clearPreferenceUser()
                    activity?.finish()
                    true
                }
                else -> true
            }
        }
    }

    private fun setVisibleButtons() {
        binding.btnNot.visibility = View.VISIBLE
        binding.btnHot.visibility = View.VISIBLE
    }

    private fun sendEmailClickListener() {
        binding.sendEmail.setOnClickListener {
            sendEmail()
        }
    }

    private fun sendEmail() {
        val user = preferencesUtil.getUser()

        val recipient = user?.email
        val text = getString(R.string.email_text)
        val sendMassageWithEmail = Intent(Intent.ACTION_SENDTO)

        sendMassageWithEmail.data = Uri.parse(DATA_SEND_EMAIL_INTENT)
        sendMassageWithEmail.type = (TYPE_SEND_EMAIL_INTENT)
        sendMassageWithEmail.putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
        sendMassageWithEmail.putExtra(Intent.EXTRA_TEXT, text + EMPTY_STRING + recipient)

        try {
            startActivity(Intent.createChooser(sendMassageWithEmail,
                getString(R.string.send_email)))
        } catch (e: Exception) {
            Toast.makeText(this.context, e.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun clickButtonsHotOrNotListener() {
        setRandomFriend()
        binding.btnHot.setOnClickListener {
            getFriend()
        }
        binding.btnNot.setOnClickListener {
            getFriend()
        }
    }

    private fun setRandomFriend() {
        val randomFriend = friendRepository.getAllSavedFriends().random()

        binding.imgFriend.setImageResource(randomFriend.image)
        binding.friendName.text = randomFriend.name
        binding.friendEmail.text = randomFriend.email
        setFriendCharacteristics(randomFriend.characteristics)
        checkForHotName()
    }

    private fun checkForHotName() {
        setVisibleButtons()
        when (binding.friendName.text) {
            HOT_NAME -> {
                binding.btnNot.visibility = View.INVISIBLE
            }
            NOT_HOT_NAME -> {
                binding.btnHot.visibility = View.INVISIBLE
            }
            else -> {
                setVisibleButtons()
            }
        }
    }

    private fun getFriend(): Friend {
        setRandomFriend()
        return friendRepository.getAllSavedFriends().random()
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