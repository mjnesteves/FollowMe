package com.followme.data.medicacao

enum class Frequencia {
    Diario,
    Semanal,
    Mensal
}

fun getFrequencia(): List<Frequencia> {
    val listaFrequencia = mutableListOf<Frequencia>()
    listaFrequencia.add(Frequencia.Diario)
    listaFrequencia.add(Frequencia.Semanal)
    listaFrequencia.add(Frequencia.Mensal)

    return listaFrequencia
}