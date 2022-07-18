package com.example.hotornot.ui

import android.widget.TextView
import androidx.databinding.BindingAdapter

class BindingAdapter {

    companion object {
        @BindingAdapter("genderIcon", "genderType", requireAll = false)
        @JvmStatic
        fun TextView.setImageViewResource(drawableResource: Int, stringResource: Int) {
            if (stringResource > 0)
                text = context.getString(stringResource)
            if (drawableResource > 0)
                setCompoundDrawablesWithIntrinsicBounds(drawableResource, 0, 0, 0)
        }
    }
}