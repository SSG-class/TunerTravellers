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
import com.example.tunertravellers.databinding.FragmentRecuperacionContrasenaBinding
import viewmodels.RecuperacionUiEvent
import viewmodels.RecuperacionViewModel
import kotlinx.coroutines.launch

class RecuperacionContrasena : Fragment() {

    private var _binding: FragmentRecuperacionContrasenaBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RecuperacionViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecuperacionContrasenaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                // Estado: error del correo y botón habilitado
                launch {
                    viewModel.uiState.collect { state ->
                        binding.tilEmail.error = state.emailError?.let { getString(it) }
                        binding.btnSubmitRecovery.isEnabled = state.isSubmitEnabled
                    }
                }

                // Eventos de un solo uso
                launch {
                    viewModel.uiEvent.collect { event ->
                        when (event) {
                            is RecuperacionUiEvent.NavigateToLogin -> {
                                val navHostFragment = requireActivity().supportFragmentManager
                                    .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
                                navHostFragment.navController
                                    .navigate(R.id.action_recuperar_to_InicioSesion)
                            }

                            is RecuperacionUiEvent.ShowError -> {
                                Toast.makeText(
                                    requireActivity(),
                                    getString(event.messageResId),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            }
        }

        binding.etRecoveryEmail.doOnTextChanged { text, _, _, _ ->
            viewModel.onEmailChanged(text.toString())
        }

        binding.btnSubmitRecovery.setOnClickListener { viewModel.onSubmitClicked() }
        binding.btnBackToLogin.setOnClickListener { viewModel.onBackToLoginClicked() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}