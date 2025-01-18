package com.example.propertiapp.repository

import com.example.propertiapp.model.Properti
import com.example.propertiapp.service.PropertiService
import okio.IOException

interface PropertiRepository {
    suspend fun insertProperti(properti: Properti)
    suspend fun getProperti(): List<Properti>
    suspend fun updateProperti(idProperti: String, properti: Properti)
    suspend fun deleteProperti(idProperti: String)
    suspend fun getPropertiById(idProperti: String): Properti
}

class NetworkPropertiRepository(
    private val propertiApiService: PropertiService
) : PropertiRepository {
    override suspend fun insertProperti(properti: Properti) {
        propertiApiService.insertProperti(properti)
    }

    override suspend fun updateProperti(idProperti: String, properti: Properti) {
        propertiApiService.updateProperti(idProperti, properti)
    }

    override suspend fun deleteProperti(idProperti: String) {
        try {
            val response = propertiApiService.deleteProperti(idProperti)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete properti. HTTP Status Code: ${response.code()}")
            } else {
                // Delete berhasil
                println("Delete successful with message: ${response.message()}")
            }
        } catch (e: Exception) {
            println("Delete failed with error: ${e.message}")
            throw e
        }
    }

    override suspend fun getProperti(): List<Properti> =
        propertiApiService.getAllProperti()

    override suspend fun getPropertiById(idProperti: String): Properti {
        return propertiApiService.getPropertiById(idProperti)
    }
}