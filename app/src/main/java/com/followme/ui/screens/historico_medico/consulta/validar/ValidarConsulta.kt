package com.followme.ui.screens.historico_medico.consulta.validar


// Função para validar a introdução dos dados para a consulta

fun validarConsulta(
    especialidade: String,
    hospital: String,
    horaConsulta: String,
    dataConsulta: String
): Boolean {

    // Devolde true se todas as variáveis devolverem ums tring que não seja vazia

    return especialidade != "" && hospital != "" && horaConsulta != "" && dataConsulta != ""
}

