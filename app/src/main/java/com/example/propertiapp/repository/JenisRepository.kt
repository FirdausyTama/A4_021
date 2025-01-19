package com.example.propertiapp.repository

import com.example.propertiapp.model.Jenis
import com.example.propertiapp.service.PropertiService
import okio.IOException

interface JenisRepository {
    suspend fun insertJenis(jenis: Jenis)
    suspend fun getJenis(): List<Jenis>
    suspend fun updateJenis(idJenis: String, jenis: Jenis)
    suspend fun deleteJenis(idJenis: String)
    suspend fun getJenisById(idJenis: String): Jenis
}

class NetworkJenisRepository(
    private val propertiApiService: PropertiService
) : JenisRepository {
    override suspend fun insertJenis(jenis: Jenis) {
        propertiApiService.insertJenis(jenis)
    }

    override suspend fun updateJenis(idJenis: String, jenis: Jenis) {
        propertiApiService.updateJenis(idJenis, jenis)
    }

    override suspend fun deleteJenis(idJenis: String) {
        try {
            val response = propertiApiService.deleteJenis(idJenis)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete jenis. HTTP Status Code: ${response.code()}")
            } else {
                // Delete berhasil
                println("Delete successful with message: ${response.message()}")
            }
        } catch (e: Exception) {
            println("Delete failed with error: ${e.message}")
            throw e
        }
    }

    override suspend fun getJenis(): List<Jenis> =
        propertiApiService.getAllJenis()

    override suspend fun getJenisById(idJenis: String): Jenis {
        return propertiApiService.getJenisById(idJenis)
    }
}