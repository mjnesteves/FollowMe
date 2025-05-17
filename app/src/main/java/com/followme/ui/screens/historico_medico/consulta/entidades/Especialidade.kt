package com.followme.ui.screens.historico_medico.consulta.entidades

enum class Especialidade(val displayName: String) {
    Anestesiologia("Anestesiologia"),
    Cardiologia("Cardiologia"),
    CirurgiaGeral("Cirurgia Geral"),
    Estomatologia("Estomatologia"),
    Neurologia("Neurologia"),
    MedicinaFamiliar("Medicina Familiar"),
    Oftalmologia("Oftalmologia"),
    Ortopedia("Ortopedia"),
    Reumatologia("Reumatologia"),
    Urologia("Urologia")

}

fun getEspecialidade(): List<Especialidade> {
    return Especialidade.entries

}