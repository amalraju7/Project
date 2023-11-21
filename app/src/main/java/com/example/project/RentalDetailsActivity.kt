package com.example.project


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.project.databinding.ActivityRentalDetailsBinding

class RentalDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRentalDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRentalDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val property = intent.getSerializableExtra("PROPERTY") as RentalProperty


        binding.propertyType.text = property.propertyType
        binding.propertyAddress.text = property.address
        binding.propertyDescription.text = property.description
        binding.propertyBedrooms.text = "Bedrooms: ${property.bedrooms}"
        binding.propertyBathroom.text = "Bathroom: ${property.bathroom}"
        binding.propertyKitchen.text = if (property.kitchen) "Kitchen available" else "No kitchen"
        binding.propertyAvailability.text = if (property.isAvailable) "Available" else "Not Available"
    }
}

