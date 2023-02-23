package com.example.hotornot.data.listener

import android.os.Parcelable

interface NoNetworkConnectionListener : Parcelable {
    fun reloadData()
}