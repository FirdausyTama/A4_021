package com.example.propertiapp.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Pemilik(
    @SerialName("id_pemilik")
    val idPemilik: Int = 0,

    @SerialName("nama_pemilik")
    val namaPemilik: String,

    @SerialName("kontak_pemilik")
    val kontakPemilik: String
)
