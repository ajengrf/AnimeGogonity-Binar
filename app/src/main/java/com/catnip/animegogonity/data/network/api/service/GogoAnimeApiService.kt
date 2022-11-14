package com.catnip.animegogonity.data.network.api.service

import com.catnip.animegogonity.BuildConfig
import com.catnip.animegogonity.data.network.api.model.Anime
import com.catnip.animegogonity.data.network.api.model.AnimeDetail
import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface GogoAnimeApiService {

    @GET("anime-movies")
    suspend fun getAnimeList(@Query("page") page: Int = 1): List<Anime>

    @GET("top-airing")
    suspend fun getTopAiringAnimeList(@Query("page") page: Int = 1): List<Anime>

    @GET("recent-release")
    suspend fun getRecentReleaseAnimeList(@Query("page") page: Int = 1): List<Anime>

    @GET("anime-detail/{animeId}")
    suspend fun getAnimeDetail(@Path("animeId") animeId: String): AnimeDetail

    companion object {

        @JvmStatic
        operator fun invoke(chuckerInterceptor: ChuckerInterceptor): GogoAnimeApiService {
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(chuckerInterceptor)
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL_GOGO_ANIME)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()

            return retrofit.create(GogoAnimeApiService::class.java)
        }
    }
}

/*
* STEP:
* model
* service -> karna based on model nya
* datasource
* repository
*/
