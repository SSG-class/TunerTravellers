
package com.example.tunertravellers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import com.example.tunertravellers.databinding.ActivityPantallaRegistroBinding

// Importaciones necesarias para validar el correo y detectar cambios en los campos
import android.util.Patterns
import androidx.core.widget.doOnTextChanged

class PantallaRegistro : Fragment() {

    private var _binding: ActivityPantallaRegistroBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ActivityPantallaRegistroBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Obtener el NavController ID del nav_host_fragment
        val navHostFragment = requireActivity().supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        // Botón: Crear Cuenta
        binding.btnSubmitRegister.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val email = binding.etEmail.text.toString()
            val phone = binding.etPhone.text.toString()
            val password = binding.etPassword.text.toString()
            val repeatPassword = binding.etRepeatPassword.text.toString()

            // Variables para controlar la validez de las contraseñas
            var passwordValida = true
            var repeatPasswordValida = true

            // Validación del nombre de usuario
            if (username.isBlank()) {
                binding.tilUsername.error = getString(R.string.ErrorUsuarioVacio)
            } else {
                binding.tilUsername.error = null
            }

            // Validación del correo electrónico
            if (email.isEmpty()) {
                binding.tilEmail.error = getString(R.string.ErrorCorreoVacio)
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.tilEmail.error = getString(R.string.ErrorCorreoInvalido)
            } else {
                binding.tilEmail.error = null
            }

            // Validación del teléfono
            if (phone.isBlank()) {
                binding.tilPhone.error = getString(R.string.ErrorTelefonoVacio)
            } else {
                binding.tilPhone.error = null
            }

            // Validación de la contraseña
            if (password.isEmpty()) {
                binding.tilPassword.error = getString(R.string.ErrorContraseniaVacia)
                passwordValida = false
            } else if (password.length <= 8) {
                binding.tilPassword.error = getString(R.string.ErrorContraseniaCorta)
                passwordValida = false
            } else {
                binding.tilPassword.error = null
            }

            // Comprobar que las contraseñas coincidan
            if (repeatPassword.isEmpty()) {
                binding.tilRepeatPassword.error = getString(R.string.ErrorRepetirContrasenia)
                repeatPasswordValida = false
            } else if (password != repeatPassword) {
                binding.tilRepeatPassword.error = getString(R.string.ErrorContraseniaNoCoincide)
                repeatPasswordValida = false
            } else {
                binding.tilRepeatPassword.error = null
            }

            // Comprobar que todos los campos sean válidos
            val datosCorrectos =
                !binding.tilUsername.isErrorEnabled &&
                        !binding.tilEmail.isErrorEnabled &&
                        !binding.tilPhone.isErrorEnabled &&
                        passwordValida &&
                        repeatPasswordValida

            if (datosCorrectos) {
                // Logica futura
            }
        }

        // Eliminar errores al modificar los campos
        binding.etUsername.doOnTextChanged { _, _, _, _ ->
            binding.tilUsername.error = null
        }

        binding.etEmail.doOnTextChanged { _, _, _, _ ->
            binding.tilEmail.error = null
        }

        binding.etPhone.doOnTextChanged { _, _, _, _ ->
            binding.tilPhone.error = null
        }

        binding.etPassword.doOnTextChanged { _, _, _, _ ->
            binding.tilPassword.error = null
        }

        binding.etRepeatPassword.doOnTextChanged { _, _, _, _ ->
            binding.tilRepeatPassword.error = null
        }

        // Botón: Registro con Google
        binding.btnGoogleRegister.setOnClickListener {
            // Logica futura para la autenticación de Google
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
