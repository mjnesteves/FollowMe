package com.followme.data.dataBase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "Medicamento",
    foreignKeys = [
        ForeignKey(
            entity = Utilizador::class,
            parentColumns = ["idUtilizador"],
            childColumns = ["idUtilizador"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Medicamento(
    @PrimaryKey(autoGenerate = true)
    val idMedicamento: Int = 0,

    @ColumnInfo(index = true)
    val idUtilizador: Int,  // Foreign key

    val nomeMedicamento: String,
    val quantidade: Int,
    val frequencia: String,
    val dataFim: String,
    val quandoToma: String
)