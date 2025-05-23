package com.followme.ui.screens.historico_medico.consulta.entidades


// Lista de entidades que vão aparecer no selector da especialidade em Adicionar/Editar Consulta


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

// Devolve uma lista dos items acima especificados

fun getEspecialidade(): List<Especialidade> {
    return Especialidade.entries

}