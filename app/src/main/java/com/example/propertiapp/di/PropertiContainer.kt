package com.example.propertiapp.di

import com.example.propertiapp.repository.PropertiRepository
import com.example.propertiapp.repository.NetworkPropertiRepository
import com.example.propertiapp.service.PropertiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val propertiRepository: PropertiRepository
}

class PropertiContainer : AppContainer {

    private val baseUrl = "http://10.0.2.2:80/pamTI/"
    private val json = Json { ignoreUnknownKeys = true }
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val propertiService: PropertiService by lazy {
        retrofit.create(PropertiService::class.java)
    }

    override val propertiRepository: PropertiRepository by lazy {
        NetworkPropertiRepository(propertiService)
    }
}