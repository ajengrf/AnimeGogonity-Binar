package com.catnip.animegogonity.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.catnip.animegogonity.data.network.api.model.Anime
import com.catnip.animegogonity.databinding.ItemHeaderHomeBinding
import com.catnip.animegogonity.databinding.ItemSectionAnimeBinding
//import com.catnip.animegogonity.presentation.ui.detail.AnimeDetailActivity
import com.catnip.animegogonity.presentation.uimodel.HOME_TYPE_HEADER
import com.catnip.animegogonity.presentation.uimodel.HomeItem

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class HomeAdapter : RecyclerView.Adapter<ViewHolder>() {
    val data = mutableListOf<HomeItem>()

    fun setItems(newData: List<HomeItem>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == HOME_TYPE_HEADER) {
            HomeHeaderItemViewHolder(
                ItemHeaderHomeBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        } else {
            HomeSectionItemViewHolder(
                ItemSectionAnimeBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder is HomeHeaderItemViewHolder)
            holder.bind(data[position] as HomeItem.HomeHeaderItem)
        else if (holder is HomeSectionItemViewHolder)
            holder.bind(data[position] as HomeItem.HomeSectionItem)
    }

    override fun getItemCount(): Int = data.size

    override fun getItemViewType(position: Int): Int {
        return data[position].type
    }
}


class HomeHeaderItemViewHolder(private val binding: ItemHeaderHomeBinding) :
    ViewHolder(binding.root) {

    fun bind(item: HomeItem.HomeHeaderItem) {
        binding.ivHeaderAnime.load(item.data.animeImg)
        binding.tvTitleAnime.text = item.data.animeTitle
    }

}

class HomeSectionItemViewHolder(private val binding: ItemSectionAnimeBinding) :
    ViewHolder(binding.root) {

    private val adapter: AnimeListAdapter by lazy {
        AnimeListAdapter {
//            AnimeDetailActivity.startActivity(itemView.context, it.animeId)
        }
    }

    fun bind(item: HomeItem.HomeSectionItem) {
        binding.tvTitleSection.text = itemView.context.getText(item.sectionName)
        binding.rvContents.adapter = adapter
        adapter.setItems(item.data)
    }

}