package com.example.propertiapp.service


import com.example.propertiapp.model.Pemilik
import retrofit2.Response
import retrofit2.http.Headers
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

annotation class leaderss
interface PemilikService {


    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )
    // Pemilik endpoints
    @POST("insertpemilik.php")
    suspend fun insertPemilik(@Body pemilik: Pemilik)

    @GET("bacapemilik.php")
    suspend fun getAllPemilik(): List<Pemilik>

    @GET("baca1pemilik.php")
    suspend fun getPemilikById(@Query("id_pemilik") idPemilik: String): Pemilik

    @PUT("editpemilik.php/{idPemilik}")
    suspend fun updatePemilik(@Query("id_pemilik") idPemilik: String, @Body pemilik: Pemilik)

    @DELETE("deletepemilik.php")
    suspend fun deletePemilik(@Query("id_pemilik") idPemilik: String): Response<Void>
}