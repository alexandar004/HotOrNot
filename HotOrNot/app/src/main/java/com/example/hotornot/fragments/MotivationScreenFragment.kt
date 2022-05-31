package com.example.hotornot.fragments

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import com.example.hotornot.R
import com.example.hotornot.databinding.FragmentMotivationScreenBinding

const val START_INDEX = 7
const val END_INDEX = 11
const val TEXT_SIZE_PROPORTION = 2f
const val STRING_WHO_IS_HOT = "Who is\n HOT?"

class MotivationScreenFragment : Fragment() {

    private lateinit var binding: FragmentMotivationScreenBinding
    private val spannableString = SpannableString(STRING_WHO_IS_HOT)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        clickBackArrowListener()
    }

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
        clickButtonListener()
    }

    private fun paintCharsFromTextView() {
        val color = ForegroundColorSpan(Color.RED)
        spannableString.setSpan(color, START_INDEX, END_INDEX, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        makeLetterBigger()
        binding.txtWhoIsHot.text = spannableString
    }

    private fun makeLetterBigger() {
        val sizeSpan = RelativeSizeSpan(TEXT_SIZE_PROPORTION)
        spannableString.setSpan(sizeSpan,
            START_INDEX,
            END_INDEX,
            Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
    }

    private fun clickButtonListener() {
        binding.btnIm.setOnClickListener {
            backToLastScreen()
        }
    }

    private fun backToLastScreen() {
        activity?.supportFragmentManager?.popBackStack()
    }

    private fun showMessage(message: String) {
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            Toast.makeText(
                requireContext(),
                message,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun clickBackArrowListener() {
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            showMessage((getString(R.string.cant_run_from_yourself)))
        }
    }
}