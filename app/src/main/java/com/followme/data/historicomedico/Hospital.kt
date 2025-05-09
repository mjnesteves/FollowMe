package com.followme.data.historicomedico

enum class Hospital() {
    HospitalAmatoLusitano,
    HospitalSousaMartins,
    HospitaisUniversidadeCoimbra,
    HospitalSantaMaria,
    HospitalSaoJoao

}

fun getHospital(): List<Hospital> {
    val listaHospital = mutableListOf<Hospital>()
    listaHospital.add(Hospital.HospitalAmatoLusitano)
    listaHospital.add(Hospital.HospitalSousaMartins)
    listaHospital.add(Hospital.HospitaisUniversidadeCoimbra)
    listaHospital.add(Hospital.HospitalSantaMaria)
    listaHospital.add(Hospital.HospitalSaoJoao)

    return listaHospital

}