package com.catnip.animegogonity.di

import com.catnip.animegogonity.data.network.api.datasource.GogoAnimeApiDataSource
import com.catnip.animegogonity.data.network.api.datasource.GogoAnimeApiDataSourceImpl
import com.catnip.animegogonity.data.network.api.service.GogoAnimeApiService
import com.catnip.animegogonity.data.repository.Repository
import com.catnip.animegogonity.data.repository.RepositoryImpl
import com.catnip.animegogonity.presentation.adapter.EpisodesAdapter
import com.catnip.animegogonity.presentation.adapter.HomeAdapter
import com.catnip.animegogonity.presentation.ui.detail.AnimeDetailViewModel
import com.catnip.animegogonity.presentation.ui.home.HomeViewModel
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

object AppModules {

    fun getModules(): List<Module> =
        listOf(networkModule, dataSource, repository, viewModels, common, adapter)

    private val networkModule = module {
        single { ChuckerInterceptor.Builder(androidContext()).build() }
        single { GogoAnimeApiService.invoke(get()) }
    }

    private val dataSource = module {
        single<GogoAnimeApiDataSource> { GogoAnimeApiDataSourceImpl(get()) }
    }

    private val repository = module {
        single<Repository> { RepositoryImpl(get()) }
    }

    private val viewModels = module {
        // cara 1
        // viewModelOf(::HomeViewModel)

        // cara 2
        viewModel {
            HomeViewModel(get())
        }
        viewModel {
            AnimeDetailViewModel(get(), get())
        }
    }

    private val adapter = module {
        factory { HomeAdapter() }
        factory { EpisodesAdapter() }
    }

    // contoh field injection
    private val common = module {
        single { Gson() }
    }
}