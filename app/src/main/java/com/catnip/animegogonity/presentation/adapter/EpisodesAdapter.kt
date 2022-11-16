package com.catnip.animegogonity.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.catnip.animegogonity.data.network.api.model.Episodes
import com.catnip.animegogonity.databinding.ItemEpisodeAnimeBinding

/*
* ViewModel
* - view modelnya Ambil data dari repository -> getDetailAnime
* - animeID dari item di Home (intent data yg diinject)
* - ada dua constructor nya (repository dan intent)
* - listener nya di Home di parsing ke activity anime detail
* - cara nya mirip kaya HomeViewModel, cukup panggil datanya lalu post value
*
* View
* - di observe
* - sesuaikan error, loading, success
* */

class EpisodesAdapter(var itemClick:( (Episodes) -> Unit)? = null) :
    RecyclerView.Adapter<EpisodesAdapter.EpisodesItemViewHolder>() {


    private var items: MutableList<Episodes> = mutableListOf()

    fun setItems(items: List<Episodes>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun addItems(items: List<Episodes>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun clearItems() {
        this.items.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodesItemViewHolder {
        val binding = ItemEpisodeAnimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EpisodesItemViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: EpisodesItemViewHolder, position: Int) {
        holder.bindView(items[position])
    }

    override fun getItemCount(): Int = items.size


    class EpisodesItemViewHolder(
        private val binding: ItemEpisodeAnimeBinding,
        val itemClick: ((Episodes) -> Unit)?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(item: Episodes) {
            with(item) {
                itemClick?.let {
                    itemView.setOnClickListener {
                        it(this)
                    }
                }
            }
            binding.tvEpisode.text = "Episode ${item.episodeNum}"
        }
    }

}