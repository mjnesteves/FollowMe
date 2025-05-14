package com.followme.data.medicacao

fun validarMedicacao(
    nomeMedicamento: String,
    quantidade: String,
    frequencia: String,
    dataFim: String,
    quandoToma: String

): Boolean {
    return nomeMedicamento != "" && quantidade != "" && frequencia != "" && dataFim != "" && quandoToma != ""
}