package com.fgomes.trademap_clone.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.navigation.fragment.findNavController
import com.fgomes.trademap_clone.R
import com.fgomes.trademap_clone.databinding.FragmentLoginBinding
import com.fgomes.trademap_clone.presentation.ui.MainActivity
import com.fgomes.trademap_clone.presentation.viewmodel.LoginViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class LoginFragment : Fragment(R.layout.fragment_login) {

    private val viewModel: LoginViewModel by viewModel()
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        return view

        observaUsuario()
        configuraBotaoLogin()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observaUsuario()
        configuraBotaoLogin()

    }

    private fun configuraBotaoLogin() {
        binding.button.setOnClickListener {
            val usuario = binding.textInputLayout.editText?.text.toString()
            viewModel.login(usuario)
        }
    }

    private fun observaUsuario() {
        viewModel.usuario.observe(viewLifecycleOwner, {
            (activity as MainActivity).findViewById<Toolbar>(R.id.toolbar).visibility = View.VISIBLE
            val direcao = LoginFragmentDirections.actionLoginFragmentToAcaoListaFragment()
            findNavController().navigate(direcao)
        })
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }


}