package com.followme.data.entidades

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/*

Representa a Entidade Consulta
Define a chave primária, idConsulta e a chave forasteira IdUtilizador
A chave primária é inacessível ao utilizador. É gerada automaticamente pelo sistema.
 */


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