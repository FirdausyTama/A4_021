package com.example.propertiapp.di

import com.example.propertiapp.repository.PropertiRepository
import com.example.propertiapp.repository.NetworkPropertiRepository
import com.example.propertiapp.repository.JenisRepository
import com.example.propertiapp.repository.ManajerRepository
import com.example.propertiapp.repository.NetworkJenisRepository
import com.example.propertiapp.repository.NetworkManajerRepository
import com.example.propertiapp.repository.PemilikRepository
import com.example.propertiapp.repository.NetworkPemilikRepository
import com.example.propertiapp.service.ManajerService
import com.example.propertiapp.service.PemilikService
import com.example.propertiapp.service.PropertiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val propertiRepository: PropertiRepository
    val jenisRepository: JenisRepository
    val pemilikRepository: PemilikRepository
    val manajerRepository: ManajerRepository
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

    private val pemilikService: PemilikService by lazy {
        retrofit.create(PemilikService::class.java)
    }

    private val manajerService: ManajerService by lazy {
        retrofit.create(ManajerService::class.java)
    }

    override val propertiRepository: PropertiRepository by lazy {
        NetworkPropertiRepository(propertiService)
    }

    override val jenisRepository: JenisRepository by lazy {
        NetworkJenisRepository(propertiService)
    }

    override val pemilikRepository: PemilikRepository by lazy {
        NetworkPemilikRepository(pemilikService)
    }

    override val manajerRepository: ManajerRepository by lazy {
        NetworkManajerRepository(manajerService)
    }
}