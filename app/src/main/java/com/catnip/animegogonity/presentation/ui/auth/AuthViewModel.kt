package com.catnip.animegogonity.presentation.ui.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catnip.animegogonity.base.wrapper.Resource
import com.catnip.animegogonity.data.firebase.model.User
import com.catnip.animegogonity.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthViewModel(private val userRepository: UserRepository) : ViewModel() {
    val loginResult = MutableLiveData<Resource<Pair<Boolean, User?>>>()

    fun authenticateGoogleLogin(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            loginResult.postValue(Resource.Loading())
            loginResult.postValue(userRepository.signInWithCredential(token))
        }
    }
}