package com.example.project


import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.project.databinding.ItemRentalPropertyBinding

class RentalAdapter(private var rentalProperties: List<RentalProperty>) :
    RecyclerView.Adapter<RentalAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRentalPropertyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val property = rentalProperties[position]


        holder.binding.propertyType.text = property.propertyType
        holder.binding.propertyAddress.text = property.address
        holder.binding.propertyAvailability.text = if (property.isAvailable) "Available" else "Not Available"


        holder.itemView.setOnClickListener {

            val intent = Intent(holder.itemView.context, RentalDetailsActivity::class.java)
            intent.putExtra("PROPERTY", property)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = rentalProperties.size

    fun updateList(newList: List<RentalProperty>) {
        rentalProperties = newList
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ItemRentalPropertyBinding) : RecyclerView.ViewHolder(binding.root)
}

