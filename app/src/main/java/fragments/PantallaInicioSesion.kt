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
import com.example.tunertravellers.R
import com.example.tunertravellers.databinding.ActivityPantallaInicioSesionBinding
import viewmodels.LoginUiEvent
import viewmodels.LoginViewModel
import kotlinx.coroutines.launch

class PantallaInicioSesion : Fragment() {

    private var _binding: ActivityPantallaInicioSesionBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ActivityPantallaInicioSesionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                // errores en los TextInputLayout y botón habilitado
                launch {
                    viewModel.uiState.collect { state ->
                        binding.tilEmail.error = state.emailError?.let { getString(it) }
                        binding.tilPhone.error = state.phoneError?.let { getString(it) }
                        binding.tilPassword.error = state.passwordError?.let { getString(it) }
                        binding.btnSubmitRegister.isEnabled = state.isSubmitEnabled
                    }
                }

                // Eventos de un solo uso
                launch {
                    viewModel.uiEvent.collect { event ->
                        when (event) {
                            is LoginUiEvent.NavigateToRecoverPassword -> {
                                val navHostFragment = requireActivity().supportFragmentManager
                                    .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
                                navHostFragment.navController
                                    .navigate(R.id.action_inicioSesion_to_recuperar)
                            }

                            is LoginUiEvent.ShowError -> {
                                Toast.makeText(
                                    requireActivity(),
                                    getString(event.messageResId),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            is LoginUiEvent.NavigateToHome -> {
                                val navHostFragment = requireActivity().supportFragmentManager
                                    .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
                                navHostFragment.navController.navigate(R.id.action_inicioSesion_to_home)
                            }

                            LoginUiEvent.NavigateToHome -> TODO()
                        }
                    }
                }
            }
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

        binding.btnSubmitRegister.setOnClickListener { viewModel.onSubmitClicked() }
        binding.btnGoogleRegister.setOnClickListener { viewModel.onGoogleClicked() }
        binding.btnForgotPassword.setOnClickListener { viewModel.onForgotPasswordClicked() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}