package uz.nurlibaydev.onlinemathgame.presentation.main.players

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.nurlibaydev.onlinemathgame.R
import uz.nurlibaydev.onlinemathgame.data.models.PlayerData
import uz.nurlibaydev.onlinemathgame.databinding.ItemPlayerBinding
import uz.nurlibaydev.onlinemathgame.utils.extenions.onClick

class PlayersAdapter :
    ListAdapter<PlayerData, PlayersAdapter.PlayerViewHolder>(PlayersItemCallBack) {

    inner class PlayerViewHolder(private val binding: ItemPlayerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind() {
            val item = getItem(absoluteAdapterPosition)
            binding.apply {

                tvDate.text = "Since ${item.date}"

                tvScore.text = "${item.score} score"

                Glide
                    .with(root.context)
                    .load(item.img)
                    .placeholder(R.drawable.musk)
                    .centerCrop()
                    .into(ivPlayer)
                tvPlayerName.text = item.name

                root.onClick {
                    itemClick.invoke(item)
                }
            }
        }
    }

    var itemClick: (data: (PlayerData)) -> Unit = {}
    fun setOnItemClickListener(block: (PlayerData) -> Unit) {
        itemClick = block
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        return PlayerViewHolder(
            ItemPlayerBinding.bind(
                LayoutInflater.from(parent.context).inflate(R.layout.item_player, parent, false)
            )
        )
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        holder.bind()
    }
}

object PlayersItemCallBack : DiffUtil.ItemCallback<PlayerData>() {
    override fun areItemsTheSame(oldItem: PlayerData, newItem: PlayerData): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PlayerData, newItem: PlayerData): Boolean {
        return oldItem.name == newItem.name && oldItem.img == newItem.img && oldItem.score == newItem.score
    }
}