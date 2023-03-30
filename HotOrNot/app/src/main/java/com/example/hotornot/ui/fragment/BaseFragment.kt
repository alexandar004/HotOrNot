package com.example.hotornot.ui.fragment

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController

abstract class BaseFragment : Fragment() {

    private var activityListener: ActivityListener? = null

    protected fun isNetworkAvailable(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = cm.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activityListener?.setToolbar()
        activityListener?.setBottomNavigation()
    }

    protected fun openScreen(screenId: NavDirections) =
        findNavController().navigate(screenId)

    protected fun showMessage(message: String) =
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            Toast.makeText(
                requireContext(),
                message,
                Toast.LENGTH_SHORT
            ).show()
        }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            activityListener = context as ActivityListener?
        } catch (e: ClassCastException) {
            throw RuntimeException("$context must implement the fragment listener.")
        }
    }

    override fun onDetach() {
        super.onDetach()
        activityListener = null
    }

    interface ActivityListener {
        fun setToolbar()
        fun setBottomNavigation()
    }
}