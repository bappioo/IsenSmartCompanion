package fr.isen.bastien.isensmartcompanion.api

import fr.isen.bastien.isensmartcompanion.Models.EventModel
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("events.json")
    fun getEvents(): Call<List<EventModel>>
}