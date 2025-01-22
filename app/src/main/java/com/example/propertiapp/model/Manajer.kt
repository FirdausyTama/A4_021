package com.example.propertiapp.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Manajer(
    @SerialName("id_manajer")
    val idManajer: String,

    @SerialName("nama_manajer")
    val namaManajer: String,

    @SerialName("kontak_manajer")
    val kontakManajer: String
)