package com.example.hotornot.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hotornot.R
import com.example.hotornot.data.repository.FriendRepository
import com.example.hotornot.databinding.FragmentViewFavouriteUsersBinding
import com.example.hotornot.ui.adapter.FavoriteFriendsAdapter
import com.example.hotornot.ui.adapter.ItemListener
import com.example.hotornot.viewModel.FavoriteFriendsViewModel

private const val TYPE_SEND_EMAIL_INTENT = "text/plain"
private const val EMPTY_STRING = " "
private const val DATA_SEND_EMAIL_INTENT = "mailto"

class ViewFavouriteUsersFragment : Fragment(), ItemListener {

    private val viewModel: FavoriteFriendsViewModel by viewModels()
    lateinit var binding: FragmentViewFavouriteUsersBinding
    private var adapter = FavoriteFriendsAdapter(this)
    private lateinit var friendRepository: FriendRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentViewFavouriteUsersBinding.inflate(layoutInflater, container, false)
        friendRepository = FriendRepository.getInstance(requireContext())
        binding.recycleViewFavoriteUsers.layoutManager = LinearLayoutManager(context)
        binding.recycleViewFavoriteUsers.adapter = adapter
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter.setFriendItem(friendRepository.likedFriends)
    }

    override fun onItemClickLister() = sendEmail()

    private fun sendEmail() {
        val user = viewModel.getUser()
        val recipient = user?.email
        val text = getString(R.string.email_text)
        val messageIntent = Intent(Intent.ACTION_SENDTO)

        messageIntent.apply {
            data = Uri.parse(DATA_SEND_EMAIL_INTENT)
            type = (TYPE_SEND_EMAIL_INTENT)
            putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
            putExtra(Intent.EXTRA_TEXT, text + EMPTY_STRING + recipient)
        }
        try {
            startActivity(Intent.createChooser(messageIntent, getString(R.string.send_email)))
        } catch (e: Exception) {
            Toast.makeText(this.context, e.message, Toast.LENGTH_LONG).show()
        }
    }
}