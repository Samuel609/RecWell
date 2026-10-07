package com.example.recwell.api

import com.example.recwell.model.ClassItem
import com.example.recwell.model.Forecast
import com.example.recwell.model.Occupancy
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("occupancy")
    fun getOccupancy(): Call<List<Occupancy>>

    @GET("forecast")
    fun getForecast(): Call<List<Forecast>>

    @GET("classes")
    fun getClasses(): Call<List<ClassItem>>
}