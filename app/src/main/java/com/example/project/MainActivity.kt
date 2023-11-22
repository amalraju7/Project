package com.example.project


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project.databinding.ActivityMainBinding
import com.example.project.databinding.ActivitySearchBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Random

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private lateinit var rentalProperties: MutableList<RentalProperty>
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var adapter: RentalAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        setSupportActionBar(binding.menu)
         rentalProperties = getSavedProperties().toMutableList()

        adapter = RentalAdapter(rentalProperties)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)


        adapter.updateList(rentalProperties)

        binding.searchButton.setOnClickListener {
            val searchTerm = binding.searchEditText.text.toString()
            val filteredProperties = rentalProperties.filter { property ->
                property.address.contains(searchTerm, ignoreCase = true)
            }
            adapter.updateList(filteredProperties)
        }

        if (isLandlord()) {
            binding.addButton.visibility = View.VISIBLE
            binding.addButton.setOnClickListener {
                    val intent = Intent(this, AddPropertyActivity::class.java)
                startActivityForResult(intent, ADD_PROPERTY_REQUEST)
            }
        }

        if (!isLoggedIn()) {
            navigateToLoginActivity()
            return
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_PROPERTY_REQUEST && resultCode == RESULT_OK) {
               rentalProperties.clear()
            rentalProperties.addAll(getSavedProperties())
            adapter.updateList(rentalProperties)
        }
    }

    private fun isLoggedIn(): Boolean {
        return sharedPreferences.contains("USERNAME")
    }

    private fun isLandlord(): Boolean {
          return sharedPreferences.getBoolean("IS_LANDLORD", false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                logout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun logout() {
          val editor = sharedPreferences.edit()
        editor.remove("USERNAME")
        editor.apply()

          navigateToLoginActivity()
    }

    private fun navigateToLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun getSavedProperties(): List<RentalProperty> {
        val jsonProperties = sharedPreferences.getString("RENTAL_PROPERTIES", "[]")
        return Gson().fromJson(jsonProperties, object : TypeToken<List<RentalProperty>>() {}.type)
    }

    companion object {
        private const val ADD_PROPERTY_REQUEST = 1
    }
}