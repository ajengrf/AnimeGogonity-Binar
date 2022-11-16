package com.catnip.animegogonity.presentation.ui.detail

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catnip.animegogonity.base.wrapper.Resource
import com.catnip.animegogonity.data.network.api.model.AnimeDetail
import com.catnip.animegogonity.data.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AnimeDetailViewModel(private val repository: Repository, private val intentData: Bundle) :
    ViewModel() {

    val animeDetail = MutableLiveData<Resource<AnimeDetail>>()

    fun loadAnimeDetail() {
        viewModelScope.launch(Dispatchers.IO) {
            animeDetail.postValue(Resource.Loading())
            val animeId = intentData.getString(AnimeDetailActivity.EXTRAS_ANIME_ID)

            animeId?.let {
                animeDetail.postValue(repository.getDetailAnime(it))
            }
        }

    }
}