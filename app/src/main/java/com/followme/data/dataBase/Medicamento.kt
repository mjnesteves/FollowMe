package com.followme.data.dataBase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Medicamento")
data class Medicamento(
    @PrimaryKey(autoGenerate = true)
    val idMedicamento: Int = 0,
    val idUtilizador: Int = 0,
    val nomeMedicamento: String,
    val quantidade: Int,
    val frequencia: String,
    val dataFim: String,
    val quandoToma: String

)