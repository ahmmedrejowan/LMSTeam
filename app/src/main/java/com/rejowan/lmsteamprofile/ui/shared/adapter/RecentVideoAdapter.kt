package com.rejowan.lmsteamprofile.ui.shared.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rejowan.lmsteamprofile.R
import com.rejowan.lmsteamprofile.data.remote.response.Video
import com.rejowan.lmsteamprofile.databinding.ItemSingleVideoBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RecentVideoAdapter(private val list: List<Video>) : RecyclerView.Adapter<RecentVideoAdapter.VideoViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): RecentVideoAdapter.VideoViewHolder {
        val binding = ItemSingleVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecentVideoAdapter.VideoViewHolder, position: Int) {
        val result = list[position]

        result.playbackUrl?.let { pUrl ->
            val title = extractTitle(pUrl)
            holder.binding.headline.text = title
        }

        result.youtube?.let { yt ->

            val youtubeId = yt.split("/").last()
            val youtubeThumbnail = "https://img.youtube.com/vi/$youtubeId/0.jpg"

            Glide.with(holder.binding.root.context).load(youtubeThumbnail).placeholder(R.drawable.img_placeholder_portrait)
                .error(R.drawable.img_placeholder_portrait).into(holder.binding.newsImage)
        }

        try {
            val initDate = result.fixDate

            initDate?.let { iDate ->
                val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
                val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val date: Date? = inputFormat.parse(iDate)
                val formattedDate = outputFormat.format(date!!)
                holder.binding.date.text = formattedDate
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

        holder.binding.share.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "Watch : ${extractTitle(result.playbackUrl.toString())} \n\n${result.youtube}")
                type = "text/plain"
            }
            holder.binding.root.context.startActivity(Intent.createChooser(sendIntent, "Share"))
        }

    }

    private fun extractTitle(playbackUrl: String): String? {
        val regex = """title="([^"]+)"""".toRegex()
        return regex.find(playbackUrl)?.groupValues?.get(1)
    }

    override fun getItemCount(): Int {
        return list.size
    }


    inner class VideoViewHolder(val binding: ItemSingleVideoBinding) : RecyclerView.ViewHolder(binding.root)

}