package com.turkcell.lyraapp.ui.auth.register

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
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    private val _effect = MutableSharedFlow<RegisterEffect>(extraBufferCapacity = 1)
    val effect: SharedFlow<RegisterEffect> = _effect.asSharedFlow()

    // UI tum kullanici aksiyonlarini bu tek giristen bildirir; ViewModel state ve effect uretir.
    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.FirstNameChanged -> updateFirstName(event.value)
            is RegisterEvent.LastNameChanged -> updateLastName(event.value)
            is RegisterEvent.PhoneChanged -> updatePhoneNumber(event.value)
            is RegisterEvent.PasswordChanged -> updatePassword(event.value)
            RegisterEvent.PasswordVisibilityClicked -> togglePasswordVisibility()
            RegisterEvent.TermsAcceptanceClicked -> toggleTermsAcceptance()
            RegisterEvent.RegisterClicked -> register()
            RegisterEvent.BackClicked -> _effect.tryEmit(RegisterEffect.NavigateBack)
            RegisterEvent.LoginClicked -> _effect.tryEmit(RegisterEffect.NavigateLogin)
        }
    }

    private fun updateFirstName(value: String) {
        _uiState.update { state ->
            state.copy(firstName = value)
        }
    }

    private fun updateLastName(value: String) {
        _uiState.update { state ->
            state.copy(lastName = value)
        }
    }

    private fun updatePhoneNumber(value: String) {
        _uiState.update { state ->
            state.copy(phoneNumber = value.filter { it.isDigit() }.take(10))
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

    private fun toggleTermsAcceptance() {
        _uiState.update { state ->
            state.copy(isTermsAccepted = !state.isTermsAccepted)
        }
    }

    private fun register() {
        val currentState = _uiState.value
        if (!currentState.canSubmit) {
            _effect.tryEmit(RegisterEffect.ShowRegisterError)
            return
        }

        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(isLoading = true)
            }

            val isRegisterSuccessful = authRepository.register(
                firstName = currentState.firstName,
                lastName = currentState.lastName,
                phoneNumber = currentState.phoneNumber,
                password = currentState.password,
            )

            _uiState.update { state ->
                state.copy(isLoading = false)
            }

            _effect.emit(
                if (isRegisterSuccessful) {
                    RegisterEffect.ShowRegisterSuccess
                } else {
                    RegisterEffect.ShowRegisterError
                },
            )
        }
    }
}
