package com.followme.data.entidades

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/*
Representa a Entidade Medicamento
Define a chave primária, idMedicamento e a chave forasteira IdUtilizador
A chave primária é inacessível ao utilizador. É gerada automaticamente pelo sistema.
*/

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
    val idUtilizador: Int,
    val nomeMedicamento: String,
    val quantidade: Int,
    val frequencia: String,
    val dataFim: String,
    val quandoToma: String
)