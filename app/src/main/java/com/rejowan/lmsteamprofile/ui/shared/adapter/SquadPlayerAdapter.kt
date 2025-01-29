package com.rejowan.lmsteamprofile.ui.shared.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rejowan.lmsteamprofile.R
import com.rejowan.lmsteamprofile.data.remote.response.AllRoundedResponse
import com.rejowan.lmsteamprofile.databinding.ItemSingleSquadBinding

class SquadPlayerAdapter(private val list: List<AllRoundedResponse>) :
    RecyclerView.Adapter<SquadPlayerAdapter.SquadPlayerViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): SquadPlayerAdapter.SquadPlayerViewHolder {
        val binding = ItemSingleSquadBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SquadPlayerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SquadPlayerAdapter.SquadPlayerViewHolder, position: Int) {
        val player = list[position]

        holder.binding.playerName.text = player.firstName + " " + player.lastName

        player.playerInfo?.let { pInfo ->
            try {
                val playerInfo = pInfo.split("/")
                holder.binding.battingStyle.text = playerInfo[0]
                holder.binding.bowlingStyle.text = playerInfo[1]
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }


        Glide.with(holder.binding.root.context).load(player.userPicture).placeholder(R.drawable.img_placeholder_portrait)
            .error(R.drawable.img_placeholder_portrait).into(holder.binding.playerImage)
    }

    override fun getItemCount(): Int {
        return list.size
    }


    inner class SquadPlayerViewHolder(val binding: ItemSingleSquadBinding) : RecyclerView.ViewHolder(binding.root)

}