package com.example.hotornot.ui.fragment

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

private const val END_INDEX = 11
private const val TEXT_SIZE_PROPORTION = 2f
private const val START_INDEX = 7
private const val STRING_WHO_IS_HOT = "Who is\n HOT?"

class MotivationScreenFragment : Fragment() {

    private lateinit var binding: FragmentMotivationScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detectOnBackClick()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMotivationScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        processingTheText()
        onConfirmationButtonClicked()
    }

    private fun processingTheText() {
        val spannableString = SpannableString(STRING_WHO_IS_HOT)
        val color = ForegroundColorSpan(Color.RED)
        val sizeSpan = RelativeSizeSpan(TEXT_SIZE_PROPORTION)

        spannableString.setSpan(color, START_INDEX, END_INDEX, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(sizeSpan,
            START_INDEX,
            END_INDEX,
            Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        binding.txtWhoIsHot.text = spannableString
    }

    private fun onConfirmationButtonClicked() =
        binding.btnIm.setOnClickListener { navigateToPreviousScreen() }


    private fun navigateToPreviousScreen() =
        activity?.supportFragmentManager?.popBackStack()


    private fun showMessage(message: String) =
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            Toast.makeText(
                requireContext(),
                message,
                Toast.LENGTH_SHORT
            ).show()
        }


    private fun detectOnBackClick() =
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            showMessage((getString(R.string.cant_run_from_yourself)))
        }
}