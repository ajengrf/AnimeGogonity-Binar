package com.catnip.animegogonity.data.repository

import com.catnip.animegogonity.base.BaseRepository
import com.catnip.animegogonity.base.wrapper.Resource
import com.catnip.animegogonity.data.network.api.datasource.GogoAnimeApiDataSource
import com.catnip.animegogonity.data.network.api.model.Anime
import com.catnip.animegogonity.data.network.api.model.AnimeDetail

interface Repository {
    suspend fun getAnimeList(page: Int = 1): Resource<List<Anime>>

    suspend fun getTopAiringList(page: Int = 1): Resource<List<Anime>>

    suspend fun getRecentReleaseAnimeList(page: Int = 1): Resource<List<Anime>>

    suspend fun getDetailAnime(animeId: String): Resource<AnimeDetail>

}


class RepositoryImpl(private val networkDataSource: GogoAnimeApiDataSource) : Repository,
    BaseRepository() {
    override suspend fun getAnimeList(page: Int): Resource<List<Anime>> {
        return doNetworkCall { networkDataSource.getAnimeList(page) }
    }

    override suspend fun getTopAiringList(page: Int): Resource<List<Anime>> {
        return doNetworkCall { networkDataSource.getTopAiringList(page) }
    }

    override suspend fun getRecentReleaseAnimeList(page: Int): Resource<List<Anime>> {
        return doNetworkCall { networkDataSource.getRecentReleaseAnimeList(page) }
    }

    override suspend fun getDetailAnime(animeId: String): Resource<AnimeDetail> {
        return doNetworkCall { networkDataSource.getDetailAnime(animeId) }
    }
}