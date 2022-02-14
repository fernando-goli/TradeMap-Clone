package com.fgomes.trademap_clone.presentation.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import android.view.ViewGroup
import com.fgomes.trademap_clone.R
import com.fgomes.trademap_clone.databinding.FragmentAcaoListaBinding
import com.fgomes.trademap_clone.domain.UsuarioLogado
import com.fgomes.trademap_clone.presentation.ui.adapters.AcaoAdapter
import com.fgomes.trademap_clone.presentation.viewmodel.AcaoViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import org.koin.android.viewmodel.ext.android.viewModel

class AcaoListaFragment : Fragment(R.layout.fragment_acao_lista) {

    private var _binding: FragmentAcaoListaBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AcaoViewModel by viewModel()
    private val adapter: AcaoAdapter by lazy {
        AcaoAdapter(UsuarioLogado.usuario.acoesFavoritas.toMutableList()) {
            val direcao = AcaoListaFragmentDirections.actionAcaoListaFragmentToAcaoDetalhesFragment(it)
            findNavController().navigate(direcao)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAcaoListaBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configuraRecyclerView()
        observaUltimaAcao()
        configuraBotaoAdicionarAcao()
        observaAcaoAdicionada()
    }

    private fun observaAcaoAdicionada() {
        viewModel.acaoAdicionada.observe(viewLifecycleOwner, {
            adapter.adicionar(it)
        })
    }

    private fun configuraBotaoAdicionarAcao() {
        binding.floatingActionButton.setOnClickListener {
            val dialogView: View = LayoutInflater.from(context)
                .inflate(R.layout.dialog_acao, view as ViewGroup?, false)
            val dialog = AlertDialog.Builder(context)
                .setTitle("Adicionar")
                .setView(dialogView)
                .create()
            dialog.show()
            dialogView.findViewById<MaterialButton>(R.id.buttonAdicionarAcao).setOnClickListener {
                val codigo = dialogView.findViewById<TextInputEditText>(R.id.input).text.toString()
                viewModel.adicionarAcao(codigo)
                dialog.dismiss()
            }
        }
    }

    private fun observaUltimaAcao() {
        viewModel.getUltimo().observe(viewLifecycleOwner, {
            adapter.atualizar(it)
        })
    }

    private fun configuraRecyclerView() {
        binding.recyclerView.adapter = adapter
    }
}
