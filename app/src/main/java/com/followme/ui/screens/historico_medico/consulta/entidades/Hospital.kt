package com.followme.ui.screens.historico_medico.consulta.entidades

enum class Hospital(val displayName: String) {
    HospitalAmatoLusitano("Hospital Amato Lusitano"),
    HospitalSousaMartins("Hospital Sousa Martins"),
    HospitaisUniversidadeCoimbra("Hospitais da Universidade de Coimbra"),
    HospitalSantaMaria("Hospital de Santa Maria"),
    HospitalSaoJoao("Hospital de São João")

}

fun getHospital(): List<Hospital> {
    return Hospital.entries
}