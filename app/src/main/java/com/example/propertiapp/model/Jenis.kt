package com.example.propertiapp.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Jenis(
    @SerialName("id_jenis")
    val idJenis: Int = 0,

    @SerialName("nama_jenis")
    val namaJenis: String,

    @SerialName("deskripsi_jenis")
    val deskripsiJenis: String
)
