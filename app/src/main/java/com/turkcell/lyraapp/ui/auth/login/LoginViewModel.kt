package com.turkcell.lyraapp.ui.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.turkcell.lyraapp.data.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val _effect = MutableSharedFlow<LoginEffect>(extraBufferCapacity = 1)
    val effect: SharedFlow<LoginEffect> = _effect.asSharedFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.PhoneChanged -> updatePhoneNumber(event.value)
            is LoginEvent.PasswordChanged -> updatePassword(event.value)
            LoginEvent.PasswordVisibilityClicked -> togglePasswordVisibility()
            LoginEvent.LoginClicked -> login()
            LoginEvent.ForgotPasswordClicked -> _effect.tryEmit(LoginEffect.NavigateForgotPassword)
            LoginEvent.RegisterClicked -> _effect.tryEmit(LoginEffect.NavigateRegister)
        }
    }

    private fun updatePhoneNumber(value: String) {
        _uiState.update { state ->
            state.copy(phoneNumber = value)
        }
    }

    private fun updatePassword(value: String) {
        _uiState.update { state ->
            state.copy(password = value)
        }
    }

    private fun togglePasswordVisibility() {
        _uiState.update { state ->
            state.copy(isPasswordVisible = !state.isPasswordVisible)
        }
    }

    private fun login() {
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(isLoading = true)
            }

            val currentState = _uiState.value
            val isLoginSuccessful = authRepository.login(
                phoneNumber = currentState.phoneNumber,
                password = currentState.password,
            )

            _uiState.update { state ->
                state.copy(isLoading = false)
            }

            _effect.emit(
                if (isLoginSuccessful) {
                    LoginEffect.ShowLoginSuccess
                } else {
                    LoginEffect.ShowLoginError
                },
            )
        }
    }
}
