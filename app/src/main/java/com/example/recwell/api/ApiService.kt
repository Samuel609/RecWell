package com.example.recwell.api

import com.example.recwell.model.Occupancy
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("occupancy")
    fun getOccupancy(): Call<List<Occupancy>>
}