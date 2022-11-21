package com.catnip.animegogonity.data.repository

import com.catnip.animegogonity.base.BaseRepository
import com.catnip.animegogonity.base.wrapper.Resource
import com.catnip.animegogonity.data.firebase.UserAuthDataSource
import com.catnip.animegogonity.data.firebase.model.User

interface UserRepository {
    suspend fun signInWithCredential(token: String): Resource<Pair<Boolean, User?>>
    fun getCurrentUser(): User?
    fun logoutUser()
}

class UserRepositoryImpl(
    private val userAuthDataSource: UserAuthDataSource,
) : UserRepository, BaseRepository() {

    override suspend fun signInWithCredential(token: String): Resource<Pair<Boolean, User?>> {
        return proceed { userAuthDataSource.signInWithCredential(token) }
    }

    override fun getCurrentUser(): User? {
        return userAuthDataSource.getCurrentUser()
    }

    override fun logoutUser() {
        return userAuthDataSource.logoutUser()
    }

}