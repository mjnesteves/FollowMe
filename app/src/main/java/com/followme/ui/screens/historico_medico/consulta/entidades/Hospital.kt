package com.followme.ui.screens.historico_medico.consulta.entidades


// Lista de entidades que vão aparecer no selector do Hospital em Adicionar/Editar Consulta

enum class Hospital(val displayName: String) {
    HospitalAmatoLusitano("Hospital Amato Lusitano"),
    HospitalSousaMartins("Hospital Sousa Martins"),
    HospitaisUniversidadeCoimbra("Hospitais da Universidade de Coimbra"),
    HospitalSantaMaria("Hospital de Santa Maria"),
    HospitalSaoJoao("Hospital de São João")

}


// Devolve uma lista dos items acima especificados

fun getHospital(): List<Hospital> {
    return Hospital.entries
}