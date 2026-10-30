package com.example.tunertravellers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import com.example.tunertravellers.databinding.FragmentRecuperacionContrasenaBinding

class RecuperacionContrasena : Fragment() {

    private var _binding: FragmentRecuperacionContrasenaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecuperacionContrasenaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Obtener el NavController ID del nav_host_fragment
        val navHostFragment = requireActivity().supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        // Botón: Enviar Código
        binding.btnSubmitRecovery.setOnClickListener {
            val email = binding.etRecoveryEmail.text.toString()

            // Lgica futura
        }

        // Botón: Volver al Login
        binding.btnBackToLogin.setOnClickListener {
            navController.navigate(R.id.action_recuperar_to_InicioSesion)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
