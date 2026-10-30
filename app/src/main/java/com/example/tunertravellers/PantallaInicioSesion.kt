package com.example.tunertravellers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import com.example.tunertravellers.databinding.ActivityPantallaInicioSesionBinding

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

            // FUtura logica
        }

        // Botón: Inicio de Sesión con Google
        binding.btnGoogleRegister.setOnClickListener {
            // LOgica futura para google
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
