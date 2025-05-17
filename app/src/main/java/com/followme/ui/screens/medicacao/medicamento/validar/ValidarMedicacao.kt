package com.followme.ui.screens.medicacao.medicamento.validar

fun validarMedicacao(
    nomeMedicamento: String,
    quantidade: String,
    frequencia: String,
    dataFim: String,
    quandoToma: String

): Boolean {
    return nomeMedicamento != "" && quantidade != "" && frequencia != "" && dataFim != "" && quandoToma != ""
}