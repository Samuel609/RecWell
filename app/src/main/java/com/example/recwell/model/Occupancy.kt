package com.example.recwell.model

data class Occupancy(
    val facilityId: String,
    val facilityName: String,
    val currentCount: Int,
    val capacity: Int,
    val lastUpdated: String
)