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

annotation class leadersssz

interface JenisService {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )

    // Jenis endpoints
    @POST("insertjenis.php")
    suspend fun insertJenis(@Body jenis: Jenis)

    @GET("bacajenis.php")
    suspend fun getAllJenis(): List<Jenis>

    @GET("baca1jenis.php")
    suspend fun getJenisById(@Query("id_jenis") idJenis: Int): Jenis

    @PUT("editjenis.php/{id_jenis}")
    suspend fun updateJenis(@Query("id_jenis") idJenis: Int, @Body jenis: Jenis)

    @DELETE("deletejenis.php")
    suspend fun deleteJenis(@Query("id_jenis") idJenis: Int): Response<Void>
}