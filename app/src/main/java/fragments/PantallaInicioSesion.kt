package fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import com.example.tunertravellers.databinding.ActivityPantallaInicioSesionBinding

// Importaciones necesarias para validar el correo y detectar cambios en los campos
import android.util.Patterns
import androidx.core.widget.doOnTextChanged
import com.example.tunertravellers.R

class PantallaInicioSesion : Fragment() {

    private var _binding: ActivityPantallaInicioSesionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ActivityPantallaInicioSesionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Obtener el NavController
        val navHostFragment = requireActivity().supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        // Botón Enviar Registro / Iniciar Sesión
        binding.btnSubmitRegister.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            // Obtener el número de teléfono introducido
            val phone = binding.etPhone.text.toString().trim()

            // Validación del correo electrónico
            if (email.isEmpty()) {
                binding.tilEmail.error = getString(R.string.ErrorCorreoVacio)
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.tilEmail.error = getString(R.string.ErrorCorreoInvalido)
            } else {
                binding.tilEmail.error = null
            }

            // Validación del teléfono
            if (phone.isEmpty()) {
                binding.tilPhone.error = getString(R.string.ErrorTelefonoVacio)
            } else {
                binding.tilPhone.error = null
            }

            // Validación de la contraseña
            if (password.isEmpty()) {
                binding.tilPassword.error = getString(R.string.ErrorContraseniaVacia)
            } else if (password.length <= 8) {
                binding.tilPassword.error = getString(R.string.ErrorContraseniaCorta)
            } else {
                binding.tilPassword.error = null
            }

            // Comprobar que todos los campos sean válidos
            val datosCorrectos =
                        !binding.tilEmail.isErrorEnabled &&
                        !binding.tilPhone.isErrorEnabled &&
                        !binding.tilPassword.isErrorEnabled

            if (datosCorrectos) {
                // Futura lógica
            }
        }

        // Eliminar los errores al modificar los campos
        binding.etEmail.doOnTextChanged { _, _, _, _ ->
            binding.tilEmail.error = null
        }

        binding.etPhone.doOnTextChanged { _, _, _, _ ->
            binding.tilPhone.error = null
        }

        binding.etPassword.doOnTextChanged { _, _, _, _ ->
            binding.tilPassword.error = null
        }

        // Botón: Inicio de Sesión con Google
        binding.btnGoogleRegister.setOnClickListener {
            // Lógica futura para google
        }

        // Botón: ¿Se te ha olvidado la contraseña?
        binding.btnForgotPassword.setOnClickListener {
            navController.navigate(R.id.action_inicioSesion_to_recuperar)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
