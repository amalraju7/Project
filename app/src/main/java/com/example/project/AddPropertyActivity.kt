package com.example.project

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.project.databinding.ActivityAddPropertyBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
class AddPropertyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddPropertyBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPropertyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

        binding.addPropertyButton.setOnClickListener {
            addProperty()
        }
    }

    private fun addProperty() {
        val propertyType = binding.propertyTypeEditText.text.toString()
        val ownerName = binding.ownerNameEditText.text.toString()
        val ownerContact = binding.ownerContactEditText.text.toString()
        val bedrooms = binding.bedroomsEditText.text.toString().toIntOrNull() ?: 0
        val kitchen = binding.kitchenCheckBox.isChecked
        val bathroom = binding.bathroomEditText.text.toString().toIntOrNull() ?: 0
        val description = binding.descriptionEditText.text.toString()
        val address = binding.addressEditText.text.toString()

          if (propertyType.isNotEmpty() && address.isNotEmpty()) {
             val newProperty = RentalProperty(
                propertyType = propertyType,
                ownerName = ownerName,
                ownerContact = ownerContact,
                bedrooms = bedrooms,
                kitchen = kitchen,
                bathroom = bathroom,
                description = description,
                address = address,
                isAvailable = true   )

             val currentProperties = getSavedProperties()

             val updatedProperties = currentProperties.toMutableList()
            updatedProperties.add(newProperty)

             saveProperties(updatedProperties)

              finish()
        } else {
              }
    }

    private fun getSavedProperties(): List<RentalProperty> {
        val jsonProperties = sharedPreferences.getString("RENTAL_PROPERTIES", "[]")
        return Gson().fromJson(jsonProperties, object : TypeToken<List<RentalProperty>>() {}.type)
    }

    private fun saveProperties(properties: List<RentalProperty>) {
        val editor = sharedPreferences.edit()
        val jsonProperties = Gson().toJson(properties)
        editor.putString("RENTAL_PROPERTIES", jsonProperties)
        editor.apply()
    }
}
