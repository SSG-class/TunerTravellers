package viewmodels

sealed interface LoginUiEvent {
    object NavigateToRecoverPassword : LoginUiEvent
    object NavigateToHome : LoginUiEvent
    data class ShowError(val messageResId: Int) : LoginUiEvent

}