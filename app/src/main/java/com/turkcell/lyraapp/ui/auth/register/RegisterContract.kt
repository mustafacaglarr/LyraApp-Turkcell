package com.turkcell.lyraapp.ui.auth.register

data class RegisterUiState(
    val firstName: String = "",
    val lastName: String = "",
    val phoneNumber: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val isTermsAccepted: Boolean = false,
    val isLoading: Boolean = false,
) {
    // Form butonunun tek state kaynagi olmasi icin UI tarafinda tekrar validasyon yazilmaz.
    val canSubmit: Boolean
        get() = firstName.isNotBlank() &&
            lastName.isNotBlank() &&
            phoneNumber.length >= 10 &&
            password.length >= 8 &&
            password.any { it.isDigit() } &&
            isTermsAccepted &&
            !isLoading
}

sealed interface RegisterEvent {
    data class FirstNameChanged(val value: String) : RegisterEvent
    data class LastNameChanged(val value: String) : RegisterEvent
    data class PhoneChanged(val value: String) : RegisterEvent
    data class PasswordChanged(val value: String) : RegisterEvent
    data object PasswordVisibilityClicked : RegisterEvent
    data object TermsAcceptanceClicked : RegisterEvent
    data object RegisterClicked : RegisterEvent
    data object BackClicked : RegisterEvent
    data object LoginClicked : RegisterEvent
}

sealed interface RegisterEffect {
    data object NavigateBack : RegisterEffect
    data object NavigateLogin : RegisterEffect
    data object ShowRegisterSuccess : RegisterEffect
    data object ShowRegisterError : RegisterEffect
}
