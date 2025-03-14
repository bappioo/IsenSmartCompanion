package fr.isen.bastien.isensmartcompanion.Api

import fr.isen.bastien.isensmartcompanion.api.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkManager {
    private const val baseUrl = "https://isen-smart-companion-default-rtdb.europe-west1.firebasedatabase.app"

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}