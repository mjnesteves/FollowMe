package com.followme.ui.screens.medicacao.medicamento.entidades

enum class Frequencia(val displayName: String) {
    Diario("Frequência Diária"),
    Semanal("Frequência Semanal"),
    Mensal("Frequencia Mensal")
}

fun getFrequencia(): List<Frequencia> {
    return Frequencia.entries
}