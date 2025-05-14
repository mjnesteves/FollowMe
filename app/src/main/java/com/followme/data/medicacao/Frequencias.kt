package com.followme.data.medicacao

enum class Frequencia(val displayName: String) {
    Diario("Frequência Diária"),
    Semanal("Frequência Semanal"),
    Mensal("Frequencia Mensal")
}

fun getFrequencia(): List<Frequencia> {
    return Frequencia.entries
}