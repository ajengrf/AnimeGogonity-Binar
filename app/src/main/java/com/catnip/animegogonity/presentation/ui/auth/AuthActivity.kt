package com.catnip.animegogonity.presentation.ui.auth

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import com.catnip.animegogonity.base.BaseViewModelActivity
import com.catnip.animegogonity.base.wrapper.Resource
import com.catnip.animegogonity.databinding.ActivityAuthBinding
import com.catnip.animegogonity.presentation.ui.main.MainActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class AuthActivity :
    BaseViewModelActivity<ActivityAuthBinding, AuthViewModel>(ActivityAuthBinding::inflate) {
    private val TAG = AuthActivity::class.java.simpleName

    override val viewModel: AuthViewModel by viewModel()

    private val googleSignInClient: GoogleSignInClient by inject {
        parametersOf(this)
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    println("account $account")
                    account.idToken?.let { firebaseAuthWithGoogle(it) }
                } catch (e: ApiException) {
                    Log.w(TAG, "Google sign in failed", e)
                }
            }
        }

    private fun firebaseAuthWithGoogle(it: String) {

    }

    override fun observeData() {
        viewModel.loginResult.observe(this) {
            when (it) {
                is Resource.Empty -> {
                    println("Empty result login")
                }
                is Resource.Error -> {
                    Toast.makeText(
                        this,
                        "Failed to Login: ${it.exception?.message.orEmpty()}",
                        Toast.LENGTH_LONG
                    ).show()
                    showLoadingState(false)
                }
                is Resource.Loading -> {
                    showLoadingState(true)
                }
                is Resource.Success -> {
                    showLoadingState(false)
                    if (it.payload?.second != null) {
                        navigateToHome()
                    }
                }
            }
        }
    }

    private fun navigateToHome() {
        startActivity(Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }

    private fun showLoadingState(isShow: Boolean) {
        binding.pbLogin.isVisible = isShow
    }

    override fun initView() {
        super.initView()
        supportActionBar?.hide()
        setLoginAction()
    }

    private fun setLoginAction() {
        binding.btnSignInGoogle.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }
}