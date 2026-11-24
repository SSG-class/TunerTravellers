package viewmodels

sealed interface RecuperacionUiEvent {
    object NavigateToLogin : RecuperacionUiEvent

    data class ShowError(val messageResId: Int) : RecuperacionUiEvent
}