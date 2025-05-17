package com.followme.ui.screens.historico_medico.consulta.validar


fun validarConsulta(
    especialidade: String,
    hospital: String,
    horaConsulta: String,
    dataConsulta: String
): Boolean {
    return especialidade != "" && hospital != "" && horaConsulta != "" && dataConsulta != ""
}

