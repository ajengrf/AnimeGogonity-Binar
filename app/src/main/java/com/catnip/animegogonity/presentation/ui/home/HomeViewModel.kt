package com.catnip.animegogonity.presentation.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catnip.animegogonity.R
import com.catnip.animegogonity.base.wrapper.Resource
import com.catnip.animegogonity.data.repository.Repository
import com.catnip.animegogonity.presentation.uimodel.HomeItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// contoh constructor injection
class HomeViewModel(private val repository: Repository) : ViewModel() {

    val homeDataResult = MutableLiveData<Resource<List<HomeItem>>>()

    fun getHomeData() {
        viewModelScope.launch(Dispatchers.IO) {
            // post loading state
            homeDataResult.postValue(Resource.Loading())
            // fetch data by sequence
            val recentReleaseData = repository.getRecentReleaseAnimeList()
            val topAiringData = repository.getTopAiringList()
            val sectionAnimeListData = repository.getAnimeList()
            // manipulate
            val homeItems = mutableListOf<HomeItem>()
            homeItems.apply {
                recentReleaseData.payload?.let {
                    add(HomeItem.HomeHeaderItem(it.random()))
                }
                recentReleaseData.payload?.let {
                    add(HomeItem.HomeSectionItem(R.string.recent_release_section, it))
                }
                topAiringData.payload?.let {
                    add(HomeItem.HomeSectionItem(R.string.top_airing_section, it))
                }
                sectionAnimeListData.payload?.let {
                    add(HomeItem.HomeSectionItem(R.string.anime_list_section, it))
                }
            }

            // post the items
            if (homeItems.isNotEmpty()) {
                homeDataResult.postValue(Resource.Success(homeItems))
            } else {
                homeDataResult.postValue(Resource.Empty())
            }
        }
    }
}