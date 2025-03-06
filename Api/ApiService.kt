package fr.isen.bastien.isensmartcompanion.Api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import android.app.usage.UsageEvents
import android.telecom.Call

interface ApiService{
    @GET("events.json")
    fun getEvents(): Call<List<UsageEvents.Event>>
}