package com.example.hotornot

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.hotornot.databinding.FragmentMainScreenBinding

class MainScreenFragment : Fragment() {

    private lateinit var binding: FragmentMainScreenBinding
    private val images = listOf(R.drawable.georgi, R.drawable.nikola, R.drawable.stan)

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
}