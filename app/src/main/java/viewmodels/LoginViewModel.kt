package viewmodels

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tunertravellers.R
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<LoginUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEmailChanged(email: String) {
        _uiState.update { current ->
            val error = validateEmail(email)
            current.copy(
                email = email,
                emailError = error,
                isSubmitEnabled = isFormValid(email, current.phone, current.password,
                    error, current.phoneError, current.passwordError)
            )
        }
    }

    fun onPhoneChanged(phone: String) {
        _uiState.update { current ->
            val error = validatePhone(phone)
            current.copy(
                phone = phone,
                phoneError = error,
                isSubmitEnabled = isFormValid(current.email, phone, current.password,
                    current.emailError, error, current.passwordError)
            )
        }
    }

    fun onPasswordChanged(password: String) {
        _uiState.update { current ->
            val error = validatePassword(password)
            current.copy(
                password = password,
                passwordError = error,
                isSubmitEnabled = isFormValid(current.email, current.phone, password,
                    current.emailError, current.phoneError, error)
            )
        }
    }

    fun onSubmitClicked() {
        val current = _uiState.value
        if (!current.isSubmitEnabled) return

        viewModelScope.launch {
            val isSuccess = simulateApiCall(current.email, current.phone, current.password)

            if (isSuccess) {
                _uiEvent.send(LoginUiEvent.NavigateToHome)
            } else {
                _uiEvent.send(LoginUiEvent.ShowError(R.string.ErrorCredencialesIncorrectas))
            }
        }
    }

    fun onGoogleClicked() {
        // Lógica futura para Google
    }

    fun onForgotPasswordClicked() {
        viewModelScope.launch {
            _uiEvent.send(LoginUiEvent.NavigateToRecoverPassword)
        }
    }

    // Simulación
    // de llamada al servidor (sustitúyela por tu lógica real)
    private suspend fun simulateApiCall(email: String, phone: String, password: String): Boolean {
        delay(1500)
        return email == "admin@test.com" && phone == "111222333" && password == "123456789"
    }

    // Reglas de validación
    private fun validateEmail(email: String): Int? = when {
        email.isEmpty() -> R.string.ErrorCorreoVacio
        !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> R.string.ErrorCorreoInvalido
        else -> null
    }

    private fun validatePhone(phone: String): Int? = when {
        phone.trim().isEmpty() -> R.string.ErrorTelefonoVacio
        else -> null
    }

    private fun validatePassword(password: String): Int? = when {
        password.isEmpty() -> R.string.ErrorContraseniaVacia
        password.length <= 8 -> R.string.ErrorContraseniaCorta
        else -> null
    }

    private fun isFormValid(
        email: String, phone: String, password: String,
        emailError: Int?, phoneError: Int?, passwordError: Int?
    ): Boolean {
        return email.isNotBlank() && phone.isNotBlank() && password.isNotBlank() &&
                emailError == null && phoneError == null && passwordError == null
    }
}