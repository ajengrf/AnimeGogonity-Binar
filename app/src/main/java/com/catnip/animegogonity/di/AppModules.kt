package com.catnip.animegogonity.di

import com.catnip.animegogonity.BuildConfig
import com.catnip.animegogonity.data.firebase.FirebaseUserAuthDataSourceImpl
import com.catnip.animegogonity.data.firebase.UserAuthDataSource
import com.catnip.animegogonity.data.network.api.datasource.GogoAnimeApiDataSource
import com.catnip.animegogonity.data.network.api.datasource.GogoAnimeApiDataSourceImpl
import com.catnip.animegogonity.data.network.api.service.GogoAnimeApiService
import com.catnip.animegogonity.data.repository.AnimeRepository
import com.catnip.animegogonity.data.repository.AnimeRepositoryImpl
import com.catnip.animegogonity.data.repository.UserRepository
import com.catnip.animegogonity.data.repository.UserRepositoryImpl
import com.catnip.animegogonity.presentation.adapter.EpisodesAdapter
import com.catnip.animegogonity.presentation.adapter.HomeAdapter
import com.catnip.animegogonity.presentation.ui.auth.AuthViewModel
import com.catnip.animegogonity.presentation.ui.detail.AnimeDetailViewModel
import com.catnip.animegogonity.presentation.ui.home.HomeViewModel
import com.catnip.animegogonity.presentation.ui.main.MainViewModel
import com.catnip.animegogonity.presentation.ui.splash.SplashViewModel
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module

object AppModules {

    fun getModules(): List<Module> =
        listOf(networkModule, dataSource, repository, viewModels, common, adapter, firebase)

    private val networkModule = module {
        single { ChuckerInterceptor.Builder(androidContext()).build() }
        single { GogoAnimeApiService.invoke(get()) }
    }

    private val dataSource = module {
        single<GogoAnimeApiDataSource> { GogoAnimeApiDataSourceImpl(get()) }
    }

    private val repository = module {
        single<AnimeRepository> { AnimeRepositoryImpl(get()) }
        single<UserAuthDataSource> { FirebaseUserAuthDataSourceImpl(get()) }
        single<UserRepository> { UserRepositoryImpl(get()) }
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
        viewModelOf(::AuthViewModel)
        viewModelOf(::MainViewModel)
        viewModelOf(::SplashViewModel)

    }

    private val adapter = module {
        factory { HomeAdapter() }
        factory { EpisodesAdapter() }
    }

    // contoh field injection
    private val common = module {
        single { Gson() }
    }

    private val firebase = module {
        single { FirebaseAuth.getInstance() }
        single { params ->
            GoogleSignIn.getClient(
                params.get(),
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(
                    BuildConfig.FIREBASE_WEB_CLIENT_ID
                ).requestEmail().build()
            )
        }
    }
}