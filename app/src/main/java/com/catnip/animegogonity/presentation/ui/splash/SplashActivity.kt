package com.catnip.animegogonity.presentation.ui.splash

import android.content.Intent
import androidx.lifecycle.lifecycleScope
import com.catnip.animegogonity.base.BaseViewModelActivity
import com.catnip.animegogonity.databinding.ActivitySplashBinding
import com.catnip.animegogonity.presentation.ui.auth.AuthActivity
import com.catnip.animegogonity.presentation.ui.main.MainActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity :
    BaseViewModelActivity<ActivitySplashBinding, SplashViewModel>(ActivitySplashBinding::inflate) {

    override val viewModel: SplashViewModel by viewModel()


    override fun initView() {
        super.initView()
        supportActionBar?.hide()
        viewModel.getCurrentUser()
    }

    override fun observeData() {
        viewModel.currentUserLiveData.observe(this) { user ->
            if (user == null) {
                lifecycleScope.launch {
                    delay(1000)
                    startActivity(Intent(this@SplashActivity, AuthActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    })
                }
            } else {
                lifecycleScope.launch {
                    delay(1000)
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    })
                }
            }
        }
    }

}