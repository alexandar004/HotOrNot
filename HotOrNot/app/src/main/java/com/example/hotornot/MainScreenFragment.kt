package com.example.hotornot

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.hotornot.databinding.FragmentMainScreenBinding

class MainScreenFragment : Fragment() {

    private lateinit var binding: FragmentMainScreenBinding

    private val imageList = listOf(R.drawable.georgi, R.drawable.nikola, R.drawable.stan)

    private var random = (imageList.indices).random()

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
        random = (imageList.indices).random()
        binding.imgFriend.setImageResource(imageList[random])
        binding.friendName.text = resources.getResourceEntryName(imageList[random])
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