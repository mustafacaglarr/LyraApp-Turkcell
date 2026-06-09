package com.turkcell.lyraapp.ui.auth.login

data class LoginUiState(
    val phoneNumber: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val isLoading: Boolean = false,
)

sealed interface LoginEvent {
    data class PhoneChanged(val value: String) : LoginEvent
    data class PasswordChanged(val value: String) : LoginEvent
    data object PasswordVisibilityClicked : LoginEvent
    data object LoginClicked : LoginEvent
    data object ForgotPasswordClicked : LoginEvent
    data object RegisterClicked : LoginEvent
}

sealed interface LoginEffect {
    data object NavigateForgotPassword : LoginEffect
    data object NavigateRegister : LoginEffect
    data object ShowLoginSuccess : LoginEffect
    data object ShowLoginError : LoginEffect
}
