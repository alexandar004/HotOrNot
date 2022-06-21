package com.example.hotornot.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.hotornot.R
import com.example.hotornot.databinding.FragmentLocationScreenBinding

private const val START_PAINTED_INDEX = 6
private const val END_PAINTED_INDEX = 8

class LocationScreen : Fragment() {

    private lateinit var binding: FragmentLocationScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentLocationScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        processingTheText()
        clickBtnChangeConfirmation()
    }

    private fun processingTheText() {
        val spannableString = SpannableString(getString(R.string.lets_go))
        val color = ForegroundColorSpan(Color.RED)
        spannableString.setSpan(color,
            START_PAINTED_INDEX,
            END_PAINTED_INDEX,
            Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        binding.motivationText.text = spannableString
    }

    private fun clickBtnChangeConfirmation() {
        binding.btnChange.setOnClickListener {

        }
    }
}