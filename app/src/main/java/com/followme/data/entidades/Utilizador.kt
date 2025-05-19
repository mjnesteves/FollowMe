package com.followme.data.entidades

import androidx.room.Entity
import androidx.room.PrimaryKey

/*
Representa a Entidade Consulta
Define a chave primária, idConsulta.
A chave primária é inacessível ao utilizador. É gerada automaticamente pelo sistema.
*/



@Entity(tableName = "Utilizador")
data class Utilizador(
    @PrimaryKey(autoGenerate = true)
    val idUtilizador: Int = 0,
    val nomeUtilizador: String,

    )