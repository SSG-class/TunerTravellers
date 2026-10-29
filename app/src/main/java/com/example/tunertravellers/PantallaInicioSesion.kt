package com.example.tunertravellers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
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

        // Botón Iniciar Sesión
        binding.btnSubmitRegister.setOnClickListener {
            //futuro comprobar correo y contraseña
        }

        // Botón Iniciar con Google
        binding.btnGoogleRegister.setOnClickListener {
            // Futuro conectar google
        }

        // Navegar a Recuperar Contraseña
        binding.tvRegisterTitle.setOnClickListener {
            findNavController().navigate(R.id.action_inicioSesion_to_recuperar)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
