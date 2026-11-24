package viewmodels

data class RegistroUiState(
    val username: String = "",
    val usernameError: Int? = null,
    val email: String = "",
    val emailError: Int? = null,
    val phone: String = "",
    val phoneError: Int? = null,
    val password: String = "",
    val passwordError: Int? = null,
    val repeatPassword: String = "",
    val repeatPasswordError: Int? = null,
    val isSubmitEnabled: Boolean = false
)