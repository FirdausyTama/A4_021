package com.example.propertiapp.repository


import com.example.propertiapp.model.Manajer
import com.example.propertiapp.service.ManajerService
import okio.IOException

interface ManajerRepository {
    suspend fun insertManajer(manajer: Manajer)
    suspend fun getManajer(): List<Manajer>
    suspend fun updateManajer(idManajer: String, manajer: Manajer)
    suspend fun deleteManajer(idManajer: String)
    suspend fun getManajerById(idManajer: String): Manajer
}

class NetworkManajerRepository(
    private val manajerApiService: ManajerService
) : ManajerRepository {
    override suspend fun insertManajer(manajer: Manajer) {
        manajerApiService.insertManajer(manajer)
    }

    override suspend fun updateManajer(idManajer: String, manajer: Manajer) {
        manajerApiService.updateManajer(idManajer, manajer)
    }

    override suspend fun deleteManajer(idManajer: String) {
        try {
            val response = manajerApiService.deleteManajer(idManajer)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete manajer. HTTP Status Code: ${response.code()}")
            } else {
                // Delete berhasil
                println("Delete successful with message: ${response.message()}")
            }
        } catch (e: Exception) {
            println("Delete failed with error: ${e.message}")
            throw e
        }
    }

    override suspend fun getManajer(): List<Manajer> =
        manajerApiService.getAllManajer()

    override suspend fun getManajerById(idManajer: String): Manajer {
        return manajerApiService.getManajerById(idManajer)
    }
}