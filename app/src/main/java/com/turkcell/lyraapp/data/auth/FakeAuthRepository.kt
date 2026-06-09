package com.turkcell.lyraapp.data.auth

import javax.inject.Inject

class FakeAuthRepository @Inject constructor() : AuthRepository {
    override suspend fun login(phoneNumber: String, password: String): Boolean {
        return phoneNumber.isNotBlank() && password.isNotBlank()
    }

    override suspend fun register(
        firstName: String,
        lastName: String,
        phoneNumber: String,
        password: String,
    ): Boolean {
        // Fake repository yalnizca form akisini dogrular; gercek token, session veya network uretmez.
        return firstName.isNotBlank() &&
            lastName.isNotBlank() &&
            phoneNumber.length >= 10 &&
            password.length >= 8 &&
            password.any { it.isDigit() }
    }
}
