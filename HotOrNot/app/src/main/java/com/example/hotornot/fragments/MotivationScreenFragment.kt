package com.example.hotornot.fragments

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.hotornot.databinding.FragmentMotivationScreenBinding

class MotivationScreenFragment : Fragment() {

    private lateinit var binding: FragmentMotivationScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentMotivationScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        paintCharsFromTextView()
    }

    private fun paintCharsFromTextView() {
        val spannableString = SpannableString("Who Is HOT?")

        val fColor = ForegroundColorSpan(Color.RED)
        spannableString.setSpan(fColor, 7, 10, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        binding.txtWhoIsHot.text = spannableString
    }
}