package viewmodels

data class LoginUiState(
    val email: String = "",
    val emailError: Int? = null,
    val phone: String = "",
    val phoneError: Int? = null,
    val password: String = "",
    val passwordError: Int? = null,
    val isSubmitEnabled: Boolean = false
)