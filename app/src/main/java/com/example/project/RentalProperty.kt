package com.example.project

import java.io.Serializable

data class RentalProperty(
    val propertyType: String,
    val ownerName: String,
    val ownerContact: String,
    val bedrooms: Int,
    val kitchen: Boolean,
    val bathroom: Int,
    val description: String,
    val address: String,
    var isAvailable: Boolean
): Serializable
