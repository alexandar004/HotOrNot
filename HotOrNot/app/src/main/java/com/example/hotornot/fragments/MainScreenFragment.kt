package com.example.hotornot.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.hotornot.FriendGenerator
import com.example.hotornot.PreferencesUtil
import com.example.hotornot.R
import com.example.hotornot.databinding.FragmentMainScreenBinding
import kotlin.random.Random

const val TYPE_SEND_EMAIL_INTENT = "text/plain"
const val DATA_SEND_EMAIL_INTENT = "mailto"

class MainScreenFragment : BaseFragment() {

    private lateinit var binding: FragmentMainScreenBinding
    private lateinit var preferencesUtil: PreferencesUtil
    private val friendImages = listOf(R.drawable.georgi, R.drawable.nikola, R.drawable.stan)
    private val friendGenerator = FriendGenerator().friendList


    override

    fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentMainScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun goToNextScreen() =
        findNavController().navigate(R.id.action_mainScreenFragment_to_profileScreenFragment)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferencesUtil = PreferencesUtil.getInstance(view.context)
        buttonsClickListener()
        sendEmailClickListener()
        selectItemFromToolbar()
        showRandomFriend()
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

//    private fun showRandomImage() {
//        setVisibleButtons()
//        val randomImage = (friendImages.indices).random()
//        binding.imgFriend.setImageResource(friendImages[randomImage])
//        binding.friendName.text = resources.getResourceEntryName(friendImages[randomImage])
//        checkForHotName()
//    }

    private fun showRandomFriend() {
//        val randomFriend = friendGenerator.indices.random()
        binding.imgFriend.setImageResource(friendGenerator[0].image)
        binding.friendName.setText(friendGenerator[0].name)

        val randomIndex = Random.nextInt(friendGenerator.size)
        val randomElement = friendGenerator[randomIndex]
    }

    private fun setVisibleButtons() {
        binding.btnNot.visibility = View.VISIBLE
        binding.btnHot.visibility = View.VISIBLE
    }

    private fun checkForHotName() {
        when (binding.friendName.text) {
            resources.getResourceEntryName(R.drawable.georgi) -> {
                binding.btnNot.visibility = View.INVISIBLE
            }
            resources.getResourceEntryName(R.drawable.stan) -> {
                binding.btnHot.visibility = View.INVISIBLE
            }
            else -> {
                setVisibleButtons()
            }
        }
    }

    private fun sendEmailClickListener() = binding.sendEmail.setOnClickListener { sendEmail() }

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

    private fun buttonsClickListener() {
        binding.btnHot.setOnClickListener {
//            showRandomImage()
        }
        binding.btnNot.setOnClickListener {
//            showRandomImage()
        }
    }
}