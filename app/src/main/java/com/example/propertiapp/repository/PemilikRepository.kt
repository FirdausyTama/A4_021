package com.example.propertiapp.repository


import com.example.propertiapp.model.Pemilik
import com.example.propertiapp.service.PemilikService
import com.example.propertiapp.service.PropertiService
import okio.IOException

interface PemilikRepository {
    suspend fun insertPemilik(pemilik: Pemilik)
    suspend fun getPemilik(): List<Pemilik>
    suspend fun updatePemilik(idPemilik: String, pemilik: Pemilik)
    suspend fun deletePemilik(idPemilik: String)
    suspend fun getPemilikById(idPemilik: String): Pemilik
}

class NetworkPemilikRepository(
    private val pemilikApiService: PemilikService
) : PemilikRepository {
    override suspend fun insertPemilik(pemilik: Pemilik) {
        pemilikApiService.insertPemilik(pemilik)
    }

    override suspend fun updatePemilik(idPemilik: String, pemilik: Pemilik) {
        pemilikApiService.updatePemilik(idPemilik, pemilik)
    }

    override suspend fun deletePemilik(idPemilik: String) {
        try {
            val response = pemilikApiService.deletePemilik(idPemilik)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete pemilik. HTTP Status Code: ${response.code()}")
            } else {
                // Delete berhasil
                println("Delete successful with message: ${response.message()}")
            }
        } catch (e: Exception) {
            println("Delete failed with error: ${e.message}")
            throw e
        }
    }

    override suspend fun getPemilik(): List<Pemilik> =
        pemilikApiService.getAllPemilik()

    override suspend fun getPemilikById(idPemilik: String): Pemilik {
        return pemilikApiService.getPemilikById(idPemilik)
    }
}