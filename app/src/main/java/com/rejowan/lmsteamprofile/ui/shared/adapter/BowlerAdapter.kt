package com.rejowan.lmsteamprofile.ui.shared.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rejowan.lmsteamprofile.data.remote.response.BowlerResponse
import com.rejowan.lmsteamprofile.databinding.ItemSingleBowlingDataBinding

class BowlerAdapter(private val list: MutableList<BowlerResponse> = mutableListOf()) :
    RecyclerView.Adapter<BowlerAdapter.BatterViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): BowlerAdapter.BatterViewHolder {
        val binding = ItemSingleBowlingDataBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BatterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BowlerAdapter.BatterViewHolder, position: Int) {
        val item = list[position]

        holder.binding.name.text = item.userName
        holder.binding.overs.text = item.overs.toString()
        holder.binding.wkts.text = item.wickets.toString()
        holder.binding.avg.text = item.average.toString()
        holder.binding.eco.text = item.economy.toString()
        holder.binding.best.text = item.best.toString()
        holder.binding.threefa.text = item.threeFa.toString()
        holder.binding.nationalRank.text = item.nationalRank.toString()
        holder.binding.worldRank.text = item.worldRank.toString()

        if (item.isFormar == 1) {
            holder.binding.linearLayout.setBackgroundColor(Color.parseColor("#D0AEAE"))
        } else {
            holder.binding.linearLayout.setBackgroundColor(Color.parseColor("#FFFFFF"))
        }

    }

    fun updateList(newList: List<BowlerResponse>) {

        val diffCallback = BowlerDiffCallback(list, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        list.clear()
        list.addAll(newList)

        diffResult.dispatchUpdatesTo(this@BowlerAdapter)

    }

    fun initList(newList: List<BowlerResponse>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }


    inner class BatterViewHolder(val binding: ItemSingleBowlingDataBinding) : RecyclerView.ViewHolder(binding.root)

    class BowlerDiffCallback(
        private val oldList: List<BowlerResponse>, private val newList: List<BowlerResponse>
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