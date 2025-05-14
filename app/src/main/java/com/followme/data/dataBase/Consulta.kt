package com.followme.data.dataBase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Consulta")
data class Consulta(
    @PrimaryKey(autoGenerate = true)
    val idConsulta: Int = 0,

    val idUtilizador: Int,
    val especialidade: String,
    val hospital: String,
    val horaConsulta: String,
    val dataConsulta: String,

    )