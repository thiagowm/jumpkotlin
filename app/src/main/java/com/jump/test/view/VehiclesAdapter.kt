package com.jump.test.view

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jump.test.R
import com.jump.test.model.Vehicle

class VehiclesAdapter(private var vehicle: List<Vehicle>, context: Context) :
    RecyclerView.Adapter<VehiclesAdapter.VehicleViewHolder>() {

    class VehicleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val texViewPlacaRow: TextView = itemView.findViewById( R.id.texViewPlacaRow )
        val texViewModelRow: TextView = itemView.findViewById( R.id.texViewModelRow )
        val imageViewRowDetails: ImageView = itemView.findViewById( R.id.imageViewRowDetails )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_vehicle, parent, false)
        return VehicleViewHolder(view)
    }

    override fun getItemCount(): Int = vehicle.size

    override fun onBindViewHolder(holder: VehicleViewHolder, position: Int) {
        val vehicles = vehicle[position]
        holder.texViewPlacaRow.text = vehicles.plate
        holder.texViewModelRow.text = vehicles.model

        holder.imageViewRowDetails.setOnClickListener{
            val intent = Intent( holder.itemView.context, VehicleDetailsActivity::class.java ).apply {
                putExtra( "vehicle_id", vehicles.vehicle_id )
            }
            holder.itemView.context.startActivity( intent )
        }
    }

    fun refreshData(newVehicles: List<Vehicle>){
        vehicle = newVehicles
        notifyDataSetChanged()
    }
}