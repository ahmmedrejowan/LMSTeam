package com.rejowan.lmsteamprofile.ui.shared.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rejowan.lmsteamprofile.R
import com.rejowan.lmsteamprofile.data.remote.response.Player
import com.rejowan.lmsteamprofile.databinding.ItemSinglePlayerBinding
import com.rejowan.lmsteamprofile.databinding.ItemSingleTopPlayerBinding

class TopPlayerAdapter(private val list: List<Player>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_TOP_PLAYER = 0
        private const val VIEW_TYPE_PLAYER = 1
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_TOP_PLAYER) {
            val binding = ItemSingleTopPlayerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            TopPlayerViewHolder(binding)
        } else {
            val binding = ItemSinglePlayerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            PlayerViewHolder(binding)
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val player = list[position]
        if (holder is TopPlayerViewHolder) {
            holder.bind(player)
        } else if (holder is PlayerViewHolder) {
            holder.bind(player)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) VIEW_TYPE_TOP_PLAYER else VIEW_TYPE_PLAYER
    }


    inner class TopPlayerViewHolder(private val binding: ItemSingleTopPlayerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(player: Player) {
            val playerNameArray = player.userName.split(" ")
            val lastName = playerNameArray.last()
            val rest = playerNameArray.dropLast(1).joinToString(" ")
            binding.lastName.text = lastName
            binding.firstName.text = rest

            binding.worldRank.text = player.worldRank.toString()
            binding.nationalRank.text = player.nationalRank.toString()

            Glide.with(binding.root.context).load(player.userPicture).placeholder(R.drawable.img_player_placeholder)
                .sizeMultiplier(0.5f).error(R.drawable.img_player_placeholder).into(binding.playerImage)

        }
    }

    inner class PlayerViewHolder(private val binding: ItemSinglePlayerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(player: Player) {
            binding.playerName.text = player.userName
            val rankings = "${player.nationalRank}/${player.worldRank}"
            binding.rank.text = rankings
            binding.order.text = "0${(adapterPosition + 1)}"

            Glide.with(binding.root.context).load(player.userPicture).placeholder(R.drawable.img_player_placeholder)
                .sizeMultiplier(0.5f).error(R.drawable.img_player_placeholder).into(binding.playerImage)
        }
    }


}