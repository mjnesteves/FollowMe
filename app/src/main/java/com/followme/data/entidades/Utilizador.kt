package com.followme.data.entidades

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Utilizador")
data class Utilizador(
    @PrimaryKey(autoGenerate = true)
    val idUtilizador: Int = 0,
    val nomeUtilizador: String,


    )