package com.example.propertiapp.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Pemilik(
    @SerialName("id_pemilik")
    val idPemilik: String,

    @SerialName("nama_pemilik")
    val namaPemilik: String,

    @SerialName("kontak_pemilik")
    val kontakPemilik: String
)
