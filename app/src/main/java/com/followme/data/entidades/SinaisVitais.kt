package com.followme.data.entidades

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/*
Representa a Entidade Sinais Citais
Define a chave primária, idRegisto e a chave forasteira IdUtilizador
A chave primária é inacessível ao utilizador. É gerada automaticamente pelo sistema.
*/



@Entity(
    tableName = "SinaisVitais",
    foreignKeys = [
        ForeignKey(
            entity = Utilizador::class,
            parentColumns = ["idUtilizador"],
            childColumns = ["idUtilizador"],
            onDelete = ForeignKey.CASCADE
        )
    ]

)
data class SinaisVitais(
    @PrimaryKey(autoGenerate = true)
    val idRegisto: Int = 0,

    @ColumnInfo(index = true)
    val idUtilizador: Int = 0,
    val valor: Int,
    val dataRegisto: String
)