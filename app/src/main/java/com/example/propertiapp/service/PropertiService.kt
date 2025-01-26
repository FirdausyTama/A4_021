package com.example.propertiapp.service

import com.example.propertiapp.model.Properti
import com.example.propertiapp.model.Jenis
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

annotation class leaders

interface PropertiService {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )

    // Properti endpoints
    @POST("insertproperti.php")
    suspend fun insertProperti(@Body properti: Properti)

    @GET("bacaproperti.php")
    suspend fun getAllProperti(): List<Properti>

    @GET("baca1properti.php")
    suspend fun getPropertiById(@Query("id_properti") idProperti: Int): Properti

    @PUT("editproperti.php/{idProperti}")
    suspend fun updateProperti(@Query("id_properti") idProperti: Int, @Body properti: Properti)

    @DELETE("deleteproperti.php")
    suspend fun deleteProperti(@Query("id_properti") idProperti: Int): Response<Void>


}