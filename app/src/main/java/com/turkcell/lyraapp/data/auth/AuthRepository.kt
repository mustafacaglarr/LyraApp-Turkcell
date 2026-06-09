package com.turkcell.lyraapp.data.auth

interface AuthRepository {
    suspend fun login(phoneNumber: String, password: String): Boolean

    // Register feature ViewModel'i gercek veya fake implementasyonu bilmeden bu kontrat uzerinden kayit akisi baslatir.
    suspend fun register(
        firstName: String,
        lastName: String,
        phoneNumber: String,
        password: String,
    ): Boolean
}
