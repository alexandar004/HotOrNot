package com.example.hotornot.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hotornot.data.repository.FriendRepository
import com.example.hotornot.databinding.FragmentViewFavouriteUsersBinding
import com.example.hotornot.ui.adapter.FavoriteFriendsAdapter
import com.example.hotornot.viewModel.FavoriteFriendsViewModel

class ViewFavouriteUsersFragment : Fragment() {

    private val viewModel: FavoriteFriendsViewModel by viewModels()
    lateinit var binding: FragmentViewFavouriteUsersBinding
    private var adapter = FavoriteFriendsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentViewFavouriteUsersBinding.inflate(layoutInflater, container, false)
        binding.recycleViewFavoriteUsers.layoutManager = LinearLayoutManager(context)
        binding.recycleViewFavoriteUsers.adapter = adapter
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}