package com.example.propertiapp.repository

import com.example.propertiapp.model.Jenis
import com.example.propertiapp.service.JenisService
import com.example.propertiapp.service.PropertiService
import okio.IOException

interface JenisRepository {
    suspend fun insertJenis(jenis: Jenis)
    suspend fun getJenis(): List<Jenis>
    suspend fun updateJenis(idJenis: Int, jenis: Jenis)
    suspend fun deleteJenis(idJenis: Int)
    suspend fun getJenisById(idJenis: Int): Jenis
}

class NetworkJenisRepository(
    private val jenisApiService: JenisService
) : JenisRepository {
    override suspend fun insertJenis(jenis: Jenis) {
        jenisApiService.insertJenis(jenis)
    }

    override suspend fun updateJenis(idJenis: Int, jenis: Jenis) {
        jenisApiService.updateJenis(idJenis, jenis)
    }

    override suspend fun deleteJenis(idJenis: Int) {
        try {
            val response = jenisApiService.deleteJenis(idJenis)
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
        jenisApiService.getAllJenis()

    override suspend fun getJenisById(idJenis: Int): Jenis {
        return jenisApiService.getJenisById(idJenis)
    }
}