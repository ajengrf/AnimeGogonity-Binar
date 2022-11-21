package com.catnip.animegogonity.presentation.ui.main

import androidx.lifecycle.ViewModel
import com.catnip.animegogonity.data.repository.UserRepository

class MainViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun doLogout() {
        userRepository.logoutUser()
    }
}