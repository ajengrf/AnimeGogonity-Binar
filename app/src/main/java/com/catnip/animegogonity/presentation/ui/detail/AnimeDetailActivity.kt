package com.catnip.animegogonity.presentation.ui.detail

import android.content.Context
import android.content.Intent
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import coil.load
import com.catnip.animegogonity.R
import com.catnip.animegogonity.base.BaseViewModelActivity
import com.catnip.animegogonity.base.wrapper.Resource
import com.catnip.animegogonity.data.network.api.model.AnimeDetail
import com.catnip.animegogonity.databinding.ActivityAnimeDetailBinding
import com.catnip.animegogonity.presentation.adapter.EpisodesAdapter
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class AnimeDetailActivity : BaseViewModelActivity<ActivityAnimeDetailBinding, AnimeDetailViewModel>(
    ActivityAnimeDetailBinding::inflate
) {

    private val adapter: EpisodesAdapter by inject()

    override val viewModel: AnimeDetailViewModel by viewModel {
        parametersOf(intent.extras ?: bundleOf())
    }

    override fun initView() {
        super.initView()
        binding.rvEpisode.adapter = adapter
        viewModel.loadAnimeDetail()
    }

    override fun observeData() {
        super.observeData()
        viewModel.animeDetail.observe(this) {
            when (it) {
                is Resource.Empty -> {
                    showError()
                    setErrorMessage(getText(R.string.text_empty_data).toString())
                }
                is Resource.Error -> {
                    showError()
                    setErrorMessage(it.exception?.message.orEmpty())
                }
                is Resource.Loading -> showLoading()
                is Resource.Success -> showData(it.payload)
            }
        }
    }

    private fun showLoading() {
        binding.pbDetail.isVisible = true
        binding.groupContent.isVisible = false
    }

    private fun showError() {
        binding.pbDetail.isVisible = false
        binding.tvErrorDetail.isVisible = true
    }

    private fun setErrorMessage(msg: String) {
        binding.tvErrorDetail.text = msg
    }

    private fun showData(payload: AnimeDetail?) {
        binding.pbDetail.isVisible = false
        binding.groupContent.isVisible = true

        payload?.let {
            adapter.setItems(it.episodesList)
            showDetail(payload)
        }
    }

    private fun showDetail(payload: AnimeDetail) {
        binding.ivPoster.load(payload.animeImg)
        binding.tvStatusAnime.text = payload.status
        binding.tvTitleAnime.text = payload.animeTitle
        binding.tvGenreAnime.text = payload.genres.joinToString(", ")
        binding.tvReleaseDateAnime.text = payload.releasedDate
        binding.tvSynopsisAnime.text = payload.synopsis
    }

    companion object {
        const val EXTRAS_ANIME_ID = "EXTRAS_ANIME_ID"
        fun startActivity(context: Context, animeId: String) {
            context.startActivity(Intent(context, AnimeDetailActivity::class.java).apply {
                putExtra(EXTRAS_ANIME_ID, animeId)
            })
        }
    }
}