package com.example.propertiapp.service


import com.example.propertiapp.model.Manajer
import retrofit2.Response
import retrofit2.http.Headers
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

annotation class leadersss
interface ManajerService {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )
    // Manajer endpoints
    @POST("insertmanajer.php")
    suspend fun insertManajer(@Body manajer: Manajer)

    @GET("bacamanajer.php")
    suspend fun getAllManajer(): List<Manajer>

    @GET("baca1manajer.php")
    suspend fun getManajerById(@Query("id_manajer") idManajer: Int): Manajer

    @PUT("editmanajer.php")
    suspend fun updateManajer(@Query("id_manajer") idManajer: Int, @Body manajer: Manajer)

    @DELETE("deletemanajer.php")
    suspend fun deleteManajer(@Query("id_manajer") idManajer: Int): Response<Void>
}