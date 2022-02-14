package com.fgomes.trademap_clone.presentation.ui.adapters

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.fgomes.trademap_clone.R
import com.fgomes.trademap_clone.databinding.ItemAcaoBinding
import com.fgomes.trademap_clone.domain.Acao
import com.fgomes.trademap_clone.extension.formatarMoeda

class AcaoAdapter(
    private val acoes: MutableList<Acao> = mutableListOf(),
    private val onClick: (Acao) -> Unit
) : RecyclerView.Adapter<AcaoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemAcaoBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val acao = acoes[position]
        holder.bind(acao)
    }

    override fun getItemCount(): Int = acoes.size

    fun adicionar(acao: Acao) {
        val contemAcao = acoes.any { it.codigo == acao.codigo }
        if (!contemAcao) {
            acoes.add(acao)
            notifyDataSetChanged()
        }
    }


    fun atualizar(acao: Acao?) {
        if (acao == null) return
        acoes.find { it.codigo == acao.codigo }?.let {
            val status = if (acao.valor >= it.valor) {
                Acao.UP
            } else {
                Acao.DOWN
            }
            it.valor = acao.valor
            it.status = status
            notifyDataSetChanged()
        }
    }


    inner class ViewHolder(private val binding: ItemAcaoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(acao: Acao) {
            with(itemView) {
                binding.textViewCodigo.text = acao.codigo
                binding.textViewValor.text = acao.valor.formatarMoeda()
                if (acao.status == Acao.UP) {
                    animacaoBackground(itemView, R.color.green)
                    binding.imageViewSeta.setImageDrawable(
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.ic_baseline_arrow_drop_up_green
                        )
                    )
                } else if (acao.status == Acao.DOWN) {
                    animacaoBackground(itemView, R.color.red)
                    binding.imageViewSeta.setImageDrawable(
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.ic_baseline_arrow_drop_down_red
                        )
                    )
                }
                setOnClickListener { onClick(acao) }
            }
        }

        fun animacaoBackground(itemView: View, color: Int) {
            with(itemView) {
                binding.constraintLayout.setBackgroundColor(ContextCompat.getColor(context, color))
                Handler(Looper.getMainLooper()).postDelayed({
                    binding.constraintLayout.setBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            R.color.gray
                        )
                    )
                }, 1000)
            }
        }

    }
}