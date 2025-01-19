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
    suspend fun getPropertiById(@Query("id_properti") idProperti: String): Properti

    @PUT("editproperti.php/{idProperti}")
    suspend fun updateProperti(@Query("idProperti") idProperti: String, @Body properti: Properti)

    @DELETE("deleteproperti.php")
    suspend fun deleteProperti(@Query("id_properti") idProperti: String): Response<Void>

    // Jenis endpoints
    @POST("insertjenis.php")
    suspend fun insertJenis(@Body jenis: Jenis)

    @GET("bacajenis.php")
    suspend fun getAllJenis(): List<Jenis>

    @GET("baca1jenis.php")
    suspend fun getJenisById(@Query("id_jenis") idJenis: String): Jenis

    @PUT("editjenis.php/{idJenis}")
    suspend fun updateJenis(@Query("idJenis") idJenis: String, @Body jenis: Jenis)

    @DELETE("deletejenis.php")
    suspend fun deleteJenis(@Query("id_jenis") idJenis: String): Response<Void>
}