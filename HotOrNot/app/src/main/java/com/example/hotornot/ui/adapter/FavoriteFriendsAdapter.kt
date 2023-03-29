package com.example.hotornot.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hotornot.data.model.Friend
import com.example.hotornot.databinding.FavoriteUsersItemBinding

class FavoriteFriendsAdapter : RecyclerView.Adapter<FavoriteFriendsViewHolder>() {

    val items: MutableList<Friend?> = mutableListOf()
    lateinit var binding: FavoriteUsersItemBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteFriendsViewHolder {
        binding =
            FavoriteUsersItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteFriendsViewHolder(binding.root, binding)
    }

    override fun onBindViewHolder(holder: FavoriteFriendsViewHolder, position: Int) {
        val friend = items[position]
        if (friend != null) {
            holder.bind(friend)
        }
    }

    fun setFriendItem(favoriteFriend: List<Friend>) {
        items.clear()
        items.addAll(favoriteFriend)
        notifyDataSetChanged()
    }

    override fun getItemCount() = items.size

}

class FavoriteFriendsViewHolder(itemViewHolder: View, val binding: FavoriteUsersItemBinding) :
    RecyclerView.ViewHolder(itemViewHolder) {

    fun bind(friend: Friend) {
        binding.imgFavUser.setImageResource(friend.image)
        binding.txtFavFriendEmail.text = friend.email
        binding.txtFavoriteUserName.text = friend.name
    }
}