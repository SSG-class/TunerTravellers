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

class RecuperacionViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(RecuperacionUiState())
    val uiState: StateFlow<RecuperacionUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<RecuperacionUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEmailChanged(email: String) {
        val error = validateEmail(email)
        _uiState.update {
            it.copy(
                email = email,
                emailError = error,
                isSubmitEnabled = email.isNotBlank() && error == null
            )
        }
    }

    fun onSubmitClicked() {
        val current = _uiState.value
        if (!current.isSubmitEnabled) return

        viewModelScope.launch {
            val isSuccess = simulateApiCall(current.email)

            if (isSuccess) {
                // Lógica futura: aquí irá el envío real del código
            } else {
                _uiEvent.send(RecuperacionUiEvent.ShowError(R.string.ErrorCorreoNoRegistrado))
            }
        }
    }

    fun onBackToLoginClicked() {
        viewModelScope.launch {
            _uiEvent.send(RecuperacionUiEvent.NavigateToLogin)
        }
    }

    // Simulación de llamada al servidor (sustitúyela por tu lógica real)
    private suspend fun simulateApiCall(email: String): Boolean {
        delay(1500)
        return email == "admin@test.com"
    }

    // Regla de validación (la misma que tenías en el Fragment)
    private fun validateEmail(email: String): Int? = when {
        email.isBlank() -> R.string.ErrorCorreoVacio
        !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> R.string.ErrorCorreoInvalido
        else -> null
    }
}