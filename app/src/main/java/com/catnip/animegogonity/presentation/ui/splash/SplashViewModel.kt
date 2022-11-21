package com.catnip.animegogonity.presentation.ui.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.catnip.animegogonity.data.firebase.model.User
import com.catnip.animegogonity.data.repository.UserRepository

class SplashViewModel(
    private val userRepository: UserRepository,
) : ViewModel() {

    val currentUserLiveData = MutableLiveData<User?>()

    fun getCurrentUser() {
        currentUserLiveData.postValue(userRepository.getCurrentUser())
    }
}