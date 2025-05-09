package com.followme.data.dataBase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SinaisVitais")
data class SinaisVitais(
    @PrimaryKey(autoGenerate = true)
    val idRegisto: Int = 0,
    val valor: Int,
    val dataRegisto: String,

    )