package com.example.project


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project.databinding.ActivityMainBinding
import com.example.project.databinding.ActivitySearchBinding
import java.util.Random

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private lateinit var rentalProperties: List<RentalProperty>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)


        rentalProperties = generateRandomData()


        val adapter = RentalAdapter(rentalProperties)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)


        binding.searchButton.setOnClickListener {
            val searchTerm = binding.searchEditText.text.toString()
            val filteredProperties = rentalProperties.filter { property ->
                property.address.contains(searchTerm, ignoreCase = true)
            }
            adapter.updateList(filteredProperties)
        }
    }

    private fun generateRandomData(): List<RentalProperty> {
        val propertyList = mutableListOf<RentalProperty>()
        val random = Random()

        for (i in 1..20) {
            val property = RentalProperty(
                propertyType = "Property Type $i",
                ownerName = "Owner $i",
                ownerContact = "Contact $i",
                bedrooms = random.nextInt(5) + 1,
                kitchen = random.nextBoolean(),
                bathroom = random.nextInt(3) + 1,
                description = "Description $i",
                address = "Address $i",
                isAvailable = random.nextBoolean()
            )
            propertyList.add(property)
        }

        return propertyList
    }
}

