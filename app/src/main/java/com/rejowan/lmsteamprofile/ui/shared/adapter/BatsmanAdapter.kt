package com.rejowan.lmsteamprofile.ui.shared.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rejowan.lmsteamprofile.data.remote.response.BatterResponse
import com.rejowan.lmsteamprofile.databinding.ItemSingleBattingDataBinding

class BatsmanAdapter(private val list: MutableList<BatterResponse> = mutableListOf()) :
    RecyclerView.Adapter<BatsmanAdapter.BatterViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): BatsmanAdapter.BatterViewHolder {
        val binding = ItemSingleBattingDataBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BatterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BatsmanAdapter.BatterViewHolder, position: Int) {
        val item = list[position]

        holder.binding.firstName.text = item.firstName
        holder.binding.lastName.text = item.lastName
        holder.binding.inngs.text = item.innings.toString()
        holder.binding.runs.text = item.runs.toString()
        holder.binding.avg.text = item.average.toString()
        holder.binding.sr.text = item.strikeRate.toString()
        holder.binding.hs.text = item.highestScore.toString()
        holder.binding.fifties.text = item.fifties.toString()
        holder.binding.nationalRank.text = item.nationalRank.toString()
        holder.binding.worldRank.text = item.worldRank.toString()

        if (item.isFormar == 1) {
            holder.binding.linearLayout.setBackgroundColor(Color.parseColor("#D0AEAE"))
        } else {
            holder.binding.linearLayout.setBackgroundColor(Color.parseColor("#FFFFFF"))
        }

    }

    fun updateList(newList: List<BatterResponse>) {


        val diffCallback = BatterDiffCallback(list, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        list.clear()
        list.addAll(newList)

        diffResult.dispatchUpdatesTo(this@BatsmanAdapter)

    }

    fun initList(newList: List<BatterResponse>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }


    inner class BatterViewHolder(val binding: ItemSingleBattingDataBinding) : RecyclerView.ViewHolder(binding.root)

    class BatterDiffCallback(
        private val oldList: List<BatterResponse>, private val newList: List<BatterResponse>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].userId == newList[newItemPosition].userId
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }


}