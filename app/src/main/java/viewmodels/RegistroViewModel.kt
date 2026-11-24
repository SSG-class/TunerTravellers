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

class RegistroViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(RegistroUiState())
    val uiState: StateFlow<RegistroUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<RegistroUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onUsernameChanged(username: String) {
        _uiState.update {
            it.copy(username = username, usernameError = validateUsername(username)).withSubmitState()
        }
    }

    fun onEmailChanged(email: String) {
        _uiState.update {
            it.copy(email = email, emailError = validateEmail(email)).withSubmitState()
        }
    }

    fun onPhoneChanged(phone: String) {
        _uiState.update {
            it.copy(phone = phone, phoneError = validatePhone(phone)).withSubmitState()
        }
    }

    fun onPasswordChanged(password: String) {
        _uiState.update { current ->
            // Si ya hay texto en "repetir contraseña", se vuelve a comprobar que coincidan
            val repeatError = if (current.repeatPassword.isEmpty()) current.repeatPasswordError
            else validateRepeatPassword(password, current.repeatPassword)

            current.copy(
                password = password,
                passwordError = validatePassword(password),
                repeatPasswordError = repeatError
            ).withSubmitState()
        }
    }

    fun onRepeatPasswordChanged(repeatPassword: String) {
        _uiState.update { current ->
            current.copy(
                repeatPassword = repeatPassword,
                repeatPasswordError = validateRepeatPassword(current.password, repeatPassword)
            ).withSubmitState()
        }
    }

    fun onSubmitClicked() {
        val current = _uiState.value
        if (!current.isSubmitEnabled) return

        viewModelScope.launch {
            val isSuccess = simulateApiCall(current.username)

            if (isSuccess) {
                _uiEvent.send(RegistroUiEvent.NavigateToHome)
            } else {
                _uiEvent.send(RegistroUiEvent.ShowError(R.string.ErrorUsuarioYaExiste))
            }
        }
    }

    fun onGoogleClicked() {
        // Lógica futura para la autenticación de Google
    }

    // Simulación de llamada al servidor (sustitúyela por tu lógica real)
    private suspend fun simulateApiCall(username: String): Boolean {
        delay(1500)
        return username.trim().lowercase() != "admin"
    }

    // Reglas de validación (las mismas que tenías en el Fragment)
    private fun validateUsername(username: String): Int? = when {
        username.isBlank() -> R.string.ErrorUsuarioVacio
        else -> null
    }

    private fun validateEmail(email: String): Int? = when {
        email.isEmpty() -> R.string.ErrorCorreoVacio
        !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> R.string.ErrorCorreoInvalido
        else -> null
    }

    private fun validatePhone(phone: String): Int? = when {
        phone.isBlank() -> R.string.ErrorTelefonoVacio
        else -> null
    }

    private fun validatePassword(password: String): Int? = when {
        password.isEmpty() -> R.string.ErrorContraseniaVacia
        password.length <= 8 -> R.string.ErrorContraseniaCorta
        else -> null
    }

    private fun validateRepeatPassword(password: String, repeatPassword: String): Int? = when {
        repeatPassword.isEmpty() -> R.string.ErrorRepetirContrasenia
        password != repeatPassword -> R.string.ErrorContraseniaNoCoincide
        else -> null
    }

    // Recalcula si el botón debe estar habilitado a partir del estado completo
    private fun RegistroUiState.withSubmitState(): RegistroUiState = copy(
        isSubmitEnabled = username.isNotBlank() && email.isNotBlank() && phone.isNotBlank() &&
                password.isNotEmpty() && repeatPassword.isNotEmpty() &&
                usernameError == null && emailError == null && phoneError == null &&
                passwordError == null && repeatPasswordError == null
    )
}