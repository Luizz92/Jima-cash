package com.paparazziapps.pretamistapp.modulos.tesoreria.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.paparazziapps.pretamistapp.R
import com.paparazziapps.pretamistapp.databinding.ItemDetallePrestamoBinding
import com.paparazziapps.pretamistapp.helper.*
import com.paparazziapps.pretamistapp.modulos.registro.pojo.PrestamoForm

class PrestamoDetalleAdapter : RecyclerView.Adapter<PrestamoDetalleAdapter.ViewHolder>() {



    var prestamosDetalle: MutableList<PrestamoForm> = mutableListOf()

    fun setData(listPrestamoForms: MutableList<PrestamoForm>) {
        prestamosDetalle = listPrestamoForms
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrestamoDetalleAdapter.ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_detalle_prestamo, parent, false)

        return PrestamoDetalleAdapter.ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PrestamoDetalleAdapter.ViewHolder, position: Int) {

        val currentItem = prestamosDetalle[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return prestamosDetalle.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val binding = ItemDetallePrestamoBinding.bind(itemView)

        fun bind(item: PrestamoForm){

            var position = binding.circleCount
            var deuda = binding.btnDeuda
            var nombres = binding.tvNombres
            var plazo_vto = binding.tvDiasRestantes

            //
            var plazo = getDiasRestantesFromStart(item.fecha?:"",item.plazo_vto?:0)

            //Calcular deuda de dias no pagados
            var deudaTotal = getDiasRestantesFromDateToNowMinusDiasPagados(item.fecha?:"",item.diasPagados?:0).toInt().times(item.montoDiarioAPagar?:0.0)

            itemView.apply {
                position.text = adapterPosition.toString()
                nombres.text = replaceFirstCharInSequenceToUppercase(item.nombres.toString())
                plazo_vto.text = "Se vence en ${if(plazo==1) "$plazo día" else "$plazo días"}"

                if(deudaTotal > 0)
                {
                    deuda.text = "${getDoubleWithTwoDecimalsReturnDouble(deudaTotal)} Deuda"
                }else
                {
                    deuda.apply {
                        text = "Sin Deudas"
                        standardSimpleButtonOutline()
                    }

                }
            }
        }
    }

}