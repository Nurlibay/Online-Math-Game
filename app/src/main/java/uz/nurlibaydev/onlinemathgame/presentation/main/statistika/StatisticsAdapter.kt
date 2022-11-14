package uz.nurlibaydev.onlinemathgame.presentation.main.statistika

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.nurlibaydev.onlinemathgame.R
import uz.nurlibaydev.onlinemathgame.data.models.PlayerData
import uz.nurlibaydev.onlinemathgame.databinding.ItemStatisticsBinding

/**
 *  Created by Nurlibay Koshkinbaev on 14/11/2022 21:59
 */

class StatisticsAdapter : ListAdapter<PlayerData, StatisticsAdapter.StatisticsViewHolder>(PlayerStatisticsItemCallBack) {

    inner class StatisticsViewHolder(private val binding: ItemStatisticsBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind() {
            val item = getItem(absoluteAdapterPosition)
            binding.apply {
                tvDate.text = "Since ${item.date}"
                tvScore.text = "Score: ${item.score}"
                tvWinCount.text = "Wins: ${item.winCount}"
                tvLostCount.text = "Losts: ${item.lostCount}"

                Glide
                    .with(root.context)
                    .load(item.img)
                    .placeholder(R.drawable.user)
                    .centerCrop()
                    .into(ivPlayer)
                tvPlayerName.text = item.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatisticsViewHolder {
        return StatisticsViewHolder(ItemStatisticsBinding.bind(LayoutInflater.from(parent.context).inflate(R.layout.item_statistics, parent, false)))
    }

    override fun onBindViewHolder(holder: StatisticsViewHolder, position: Int) {
        holder.bind()
    }
}

object PlayerStatisticsItemCallBack : DiffUtil.ItemCallback<PlayerData>() {
    override fun areItemsTheSame(oldItem: PlayerData, newItem: PlayerData): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PlayerData, newItem: PlayerData): Boolean {
        return oldItem.name == newItem.name && oldItem.img == newItem.img && oldItem.score == newItem.score
    }
}