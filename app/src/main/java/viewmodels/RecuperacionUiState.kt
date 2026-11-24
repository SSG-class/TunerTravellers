package viewmodels

data class RecuperacionUiState(
    val email: String = "",
    val emailError: Int? = null,
    val isSubmitEnabled: Boolean = false
)