package com.catnip.animegogonity.utils

import com.catnip.animegogonity.data.firebase.model.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resumeWithException

suspend fun <T> Task<T>.await(): T {
    return suspendCancellableCoroutine { cont ->
        addOnCompleteListener {
            if (it.exception != null) {
                cont.resumeWithException(it.exception!!)
            } else {
                cont.resume(it.result, onCancellation = null)
            }
        }

    }
}

fun FirebaseUser.toUserObject(): User {
    return User(
        displayName.orEmpty(),
        email.orEmpty(),
        photoUrl.toString()
    )
}