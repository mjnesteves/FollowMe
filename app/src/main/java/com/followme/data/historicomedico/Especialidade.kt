package com.followme.data.historicomedico

enum class Especialidade {
    Anestesiologia,
    Cardiologia,
    CirurgiaGeral,
    Estomatologia,
    Neurologia,
    Oftalmologia,
    Ortopedia,
    Reumatologia,
    Urologia

}

fun getEspecialidade(): List<Especialidade> {
    val listaEspecialidade = mutableListOf<Especialidade>()
    listaEspecialidade.add(Especialidade.Anestesiologia)
    listaEspecialidade.add(Especialidade.Cardiologia)
    listaEspecialidade.add(Especialidade.CirurgiaGeral)
    listaEspecialidade.add(Especialidade.Estomatologia)
    listaEspecialidade.add(Especialidade.Neurologia)
    listaEspecialidade.add(Especialidade.Oftalmologia)
    listaEspecialidade.add(Especialidade.Ortopedia)
    listaEspecialidade.add(Especialidade.Reumatologia)
    listaEspecialidade.add(Especialidade.Urologia)

    return listaEspecialidade

}