package com.example.hotornot

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.hotornot.databinding.FragmentMainScreenBinding

const val EMAIL_RECIPIENT = "adamqnov07@gmail.com"
const val EMAIL_TEXT = "zdr ko pr bepce"

class MainScreenFragment : Fragment()
//    , CommunicationBetweenScreens
{

    private lateinit var binding: FragmentMainScreenBinding
    private val images = listOf(R.drawable.georgi, R.drawable.nikola, R.drawable.stan)
    private lateinit var preferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentMainScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnHot.setOnClickListener {
            showRandomImage()
        }
        binding.btnNot.setOnClickListener {
            showRandomImage()
        }
        binding.sendEmail.setOnClickListener {
            sendEmail()
        }

        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.profileScreen -> {
                    findNavController().navigate(R.id.action_mainScreenFragment_to_profileScreenFragment)
                    true
                }
                R.id.logOut -> {
                    val editor: SharedPreferences.Editor = preferences.edit()
                    editor.clear()
                    editor.apply()

                    val intent = Intent(this.context, MainActivity::class.java)
                    startActivity(intent)

                    true
                }
                else -> false
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)
    }

    private fun showRandomImage() {
        setVisibleButtons()
        val randomImage = (images.indices).random()
        binding.imgFriend.setImageResource(images[randomImage])
        binding.friendName.text = resources.getResourceEntryName(images[randomImage])
        checkForHotName()
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

    private fun sendEmail() {
        val recipient = EMAIL_RECIPIENT
        val text = EMAIL_TEXT
        val sendMassageWithEmail = Intent(Intent.ACTION_SENDTO)

        sendMassageWithEmail.data = Uri.parse("mailto")
        sendMassageWithEmail.type = ("text/plain")
        sendMassageWithEmail.putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
        sendMassageWithEmail.putExtra(Intent.EXTRA_TEXT, text)

        try {
            startActivity(Intent.createChooser(sendMassageWithEmail, "Send email"))
        } catch (e: Exception) {
            Toast.makeText(this.context, e.message, Toast.LENGTH_LONG).show()
        }
    }
}