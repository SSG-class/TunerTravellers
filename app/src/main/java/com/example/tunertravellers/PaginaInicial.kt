package com.example.tunertravellers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import com.example.tunertravellers.databinding.FragmentPaginaInicialBinding

class PaginaInicial : Fragment() {

    private var _binding: FragmentPaginaInicialBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPaginaInicialBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Botón 1: Iniciar Sesión (Con la forma de tu compañero)
        binding.boton1.setOnClickListener {
            val navHostFragment = requireActivity().supportFragmentManager
                .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            val navController = navHostFragment.navController

            // Usamos tu ID de acción del nav_graph.xml
            navController.navigate(R.id.action_paginaInicio_to_pantallaInicioSesion)
        }

        // Botón 2: Registrarse (Con la forma de tu compañero)
        binding.boton2.setOnClickListener {
            val navHostFragment = requireActivity().supportFragmentManager
                .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            val navController = navHostFragment.navController

            // Usamos tu ID de acción del nav_graph.xml
            navController.navigate(R.id.action_paginaInicio_to_pantallaRegistro)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
