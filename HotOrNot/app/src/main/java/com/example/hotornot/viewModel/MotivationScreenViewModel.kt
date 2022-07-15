package com.example.hotornot.viewModel

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.example.hotornot.ui.fragment.MotivationScreenFragmentDirections

private const val FIRST_INDEX = 7
private const val LAST_INDEX = 11
private const val TEXT_SIZE = 2f
private const val WHO_IS_HOT = "Who is \nHOT?"

class MotivationScreenViewModel : ViewModel() {

    private val _motivationText = MutableLiveData<SpannableString>()
    val motivationText: LiveData<SpannableString> = _motivationText
    private val _navigationLiveData = MutableLiveData<NavDirections>()
    val navigationLiveData: LiveData<NavDirections> = _navigationLiveData

    init {
        getMotivationText()
    }

    private fun getMotivationText() {
        _motivationText.value = getQuestionStyle()
    }

    private fun getQuestionStyle(): SpannableString {
        val spannable =
            SpannableString(WHO_IS_HOT)
        spannable.setSpan(
            ForegroundColorSpan(Color.RED),
            FIRST_INDEX,
            LAST_INDEX,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannable.setSpan(
            RelativeSizeSpan(TEXT_SIZE),
            FIRST_INDEX,
            LAST_INDEX,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return spannable
    }

    fun onMotivationButtonClicked() {
        val navDirection =
            MotivationScreenFragmentDirections.actionMotivationScreenToMainScreenFragment()
        _navigationLiveData.postValue(navDirection)
    }
}