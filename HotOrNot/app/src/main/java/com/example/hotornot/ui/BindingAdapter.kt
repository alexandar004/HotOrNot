package com.example.hotornot.ui

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter

private const val NO_VALUE = 0

class BindingAdapter {

    companion object {
        @BindingAdapter("genderIcon", "genderType", requireAll = false)
        @JvmStatic
        fun TextView.setImageViewResource(drawableResource: Int, stringResource: Int) {
            if (stringResource > NO_VALUE)
                text = context.getString(stringResource)
            if (drawableResource > NO_VALUE)
                setCompoundDrawablesWithIntrinsicBounds(
                    drawableResource,
                    NO_VALUE,
                    NO_VALUE,
                    NO_VALUE
                )
        }

        @BindingAdapter("setVisibility")
        @JvmStatic
        fun setVisibility(view: View, value: Boolean) {
            view.visibility = if (value) View.VISIBLE else View.GONE
        }
    }
}