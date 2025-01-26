package com.example.propertiapp.repository


import com.example.propertiapp.model.Manajer
import com.example.propertiapp.service.ManajerService
import okio.IOException

interface ManajerRepository {
    suspend fun insertManajer(manajer: Manajer)
    suspend fun getManajer(): List<Manajer>
    suspend fun updateManajer(idManajer: Int, manajer: Manajer)
    suspend fun deleteManajer(idManajer: Int)
    suspend fun getManajerById(idManajer: Int): Manajer
}

class NetworkManajerRepository(
    private val manajerApiService: ManajerService
) : ManajerRepository {
    override suspend fun insertManajer(manajer: Manajer) {
        manajerApiService.insertManajer(manajer)
    }

    override suspend fun updateManajer(idManajer: Int, manajer: Manajer) {
        manajerApiService.updateManajer(idManajer, manajer)
    }

    override suspend fun deleteManajer(idManajer: Int) {
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

    override suspend fun getManajerById(idManajer: Int): Manajer {
        return manajerApiService.getManajerById(idManajer)
    }
}