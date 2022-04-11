package com.example.hotornot

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FindNewFriendAdapter : RecyclerView.Adapter<FriendViewHolder>() {

    private val friendList: MutableList<Friend> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        val friend = friendList[position]
        holder.bind(friend)
    }

    fun setItems(friends: List<Friend>) {
        friendList.clear()
        friendList.addAll(friends)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = friendList.size
}

class FriendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val image: ImageView = itemView.findViewById(R.id.imgFriend)
    private val name: TextView = itemView.findViewById(R.id.friendName)

    fun bind(friend: Friend) {
        image.setImageResource(friend.image)
        image.textAlignment = friend.image
    }
}

// опраавяме гит игнор
// трием ги от директория
// адваме комитваме и пушваме
// проверка