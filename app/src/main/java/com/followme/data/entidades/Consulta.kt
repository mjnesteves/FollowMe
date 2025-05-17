package com.followme.data.entidades

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "Consulta",
    foreignKeys = [
        ForeignKey(
            entity = Utilizador::class,
            parentColumns = ["idUtilizador"],
            childColumns = ["idUtilizador"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Consulta(
    @PrimaryKey(autoGenerate = true)
    val idConsulta: Int = 0,

    @ColumnInfo(index = true)
    val idUtilizador: Int,

    val especialidade: String,
    val hospital: String,
    val horaConsulta: String,
    val dataConsulta: String
)