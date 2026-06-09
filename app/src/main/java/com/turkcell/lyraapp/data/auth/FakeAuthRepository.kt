package com.turkcell.lyraapp.data.auth

import javax.inject.Inject

class FakeAuthRepository @Inject constructor() : AuthRepository {
    override suspend fun login(phoneNumber: String, password: String): Boolean {
        return phoneNumber.isNotBlank() && password.isNotBlank()
    }
}
