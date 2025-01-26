package com.example.propertiapp.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Properti(
    @SerialName("id_properti")
    val idProperti: Int = 0,

    @SerialName("nama_properti")
    val namaProperti: String,

    @SerialName("deskripsi_properti")
    val deskripsiProperti: String,

    val lokasi: String,
    val harga: String,

    @SerialName("status_properti")
    val statusProperti: String,

    @SerialName("id_jenis")
    val idJenis: String,

    @SerialName("id_pemilik")
    val idPemilik: String,

    @SerialName("id_manajer")
    val idManajer: String
)
