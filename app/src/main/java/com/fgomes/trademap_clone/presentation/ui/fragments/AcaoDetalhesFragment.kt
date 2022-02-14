package com.fgomes.trademap_clone.presentation.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.navArgs
import com.fgomes.trademap_clone.R
import com.fgomes.trademap_clone.databinding.FragmentAcaoDetalhesBinding
import com.fgomes.trademap_clone.domain.Acao
import com.fgomes.trademap_clone.extension.formatarMoeda
import com.fgomes.trademap_clone.presentation.viewmodel.AcaoViewModel
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet


import org.koin.android.viewmodel.ext.android.viewModel

class AcaoDetalhesFragment : Fragment(R.layout.fragment_acao_detalhes) {

    private var _binding: FragmentAcaoDetalhesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AcaoViewModel by viewModel()
    private val arguments by navArgs<AcaoDetalhesFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAcaoDetalhesBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val acao = arguments.acao
        observarUltimo(acao)
        observaAcoes(acao)
    }

    private fun observarUltimo(acao: Acao) {
        viewModel.getUltimo(acao.codigo).observe(viewLifecycleOwner, {
            binding.textViewCodigo.text = it.codigo
            binding.textViewValor.text = it.valor.formatarMoeda()
        })
    }

    private fun observaAcoes(acao: Acao) {
        viewModel.getTodos(acao.codigo).observe(viewLifecycleOwner, { acoes ->
            val pontos = mutableListOf<Entry>()
            acoes?.forEachIndexed { index, acao ->
                pontos.add(Entry(index.toFloat(), acao.valor.toFloat()))
            }
            criarGrafico(pontos)
        })
    }

    fun criarGrafico(pontos: MutableList<Entry>) {
        val lineDataSet = LineDataSet(pontos, "Ações").apply {
            lineWidth = 1.8f
            valueTextColor = ContextCompat.getColor(requireContext(), R.color.white)
            color = ContextCompat.getColor(requireContext(), R.color.colorAccent)
            fillColor = ContextCompat.getColor(requireContext(), R.color.colorAccent)
            circleColors =
                mutableListOf(ContextCompat.getColor(requireContext(), R.color.colorAccent))
            fillAlpha = 170
            setDrawFilled(true)
            setDrawCircles(true)
        }
        binding.lineChart.apply {
            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                setDrawGridLines(false)
            }
            axisLeft.apply {
                setDrawGridLines(false)
            }

            axisRight.isEnabled = false
            axisLeft.textColor = ContextCompat.getColor(context, android.R.color.white)
            xAxis.textColor = ContextCompat.getColor(context, android.R.color.white)
            description.isEnabled = false
            legend.isEnabled = false
            animateY(1000)
            data = LineData(lineDataSet)
        }
    }


}