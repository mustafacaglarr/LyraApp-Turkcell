package com.turkcell.lyraapp.di

import com.turkcell.lyraapp.data.auth.AuthRepository
import com.turkcell.lyraapp.data.auth.FakeAuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthModule {
    @Binds
    abstract fun bindAuthRepository(
        fakeAuthRepository: FakeAuthRepository,
    ): AuthRepository
}
