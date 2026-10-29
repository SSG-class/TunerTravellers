package com.example.tunertravellers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tunertravellers.databinding.ActivityPantallaRegistroBinding

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

        // Botón Crear Cuenta
        binding.btnSubmitRegister.setOnClickListener {
            // val username = binding.etUsername.text.toString()
            // val email = binding.etEmail.text.toString()
            // val password = binding.etPassword.text.toString()
            // val repeatPassword = binding.etRepeatPassword.text.toString()
        }

        // Botón Registro con Google
        binding.btnGoogleRegister.setOnClickListener {
            //futuro de autenticación alternativa con Google
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
