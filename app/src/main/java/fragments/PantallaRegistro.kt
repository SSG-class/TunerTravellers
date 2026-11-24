package fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.NavHostFragment
import com.example.tunertravellers.databinding.ActivityPantallaRegistroBinding
import viewmodels.RegistroUiEvent
import viewmodels.RegistroViewModel
import kotlinx.coroutines.launch
import com.example.tunertravellers.R

class PantallaRegistro : Fragment() {

    private var _binding: ActivityPantallaRegistroBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RegistroViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ActivityPantallaRegistroBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                // Estado: errores en los TextInputLayout y botón habilitado
                launch {
                    viewModel.uiState.collect { state ->
                        binding.tilUsername.error = state.usernameError?.let { getString(it) }
                        binding.tilEmail.error = state.emailError?.let { getString(it) }
                        binding.tilPhone.error = state.phoneError?.let { getString(it) }
                        binding.tilPassword.error = state.passwordError?.let { getString(it) }
                        binding.tilRepeatPassword.error = state.repeatPasswordError?.let { getString(it) }
                        binding.btnSubmitRegister.isEnabled = state.isSubmitEnabled
                    }
                }

                // Eventos de un solo uso
                launch {
                    viewModel.uiEvent.collect { event ->
                        when (event) {
                            is RegistroUiEvent.ShowError -> {
                                Toast.makeText(
                                    requireActivity(),
                                    getString(event.messageResId),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            is RegistroUiEvent.NavigateToHome -> {
                                val navHostFragment = requireActivity().supportFragmentManager
                                    .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
                                navHostFragment.navController.navigate(R.id.action_registro_to_home)
                            }

                            RegistroUiEvent.NavigateToHome -> TODO()
                        }
                    }
                }
            }
        }

        binding.etUsername.doOnTextChanged { text, _, _, _ ->
            viewModel.onUsernameChanged(text.toString())
        }
        binding.etEmail.doOnTextChanged { text, _, _, _ ->
            viewModel.onEmailChanged(text.toString())
        }
        binding.etPhone.doOnTextChanged { text, _, _, _ ->
            viewModel.onPhoneChanged(text.toString())
        }
        binding.etPassword.doOnTextChanged { text, _, _, _ ->
            viewModel.onPasswordChanged(text.toString())
        }
        binding.etRepeatPassword.doOnTextChanged { text, _, _, _ ->
            viewModel.onRepeatPasswordChanged(text.toString())
        }

        binding.btnSubmitRegister.setOnClickListener { viewModel.onSubmitClicked() }
        binding.btnGoogleRegister.setOnClickListener { viewModel.onGoogleClicked() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}