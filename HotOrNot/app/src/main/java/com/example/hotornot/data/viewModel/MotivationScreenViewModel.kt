package com.example.hotornot.data.viewModel

import android.app.Application
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDirections
import com.example.hotornot.R
import com.example.hotornot.ui.fragment.MotivationScreenFragmentDirections

private const val FIRST_INDEX = 7
private const val LAST_INDEX = 10
private const val TEXT_SIZE = 2f

class MotivationScreenViewModel(application: Application) : AndroidViewModel(application) {

    private val _motivationText = MutableLiveData<SpannableString>()
    val motivationText: LiveData<SpannableString> = _motivationText
    private val _navigationLiveData = MutableLiveData<NavDirections>()
    val navigationLiveData: LiveData<NavDirections> = _navigationLiveData

    init {
        appropriateValue()
    }

    private fun appropriateValue() {
        _motivationText.value = getQuestionStyle()
    }

    private fun getQuestionStyle(): SpannableString {
        val spannable =
            SpannableString(getApplication<Application>().getString(R.string.who_is_hot))
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