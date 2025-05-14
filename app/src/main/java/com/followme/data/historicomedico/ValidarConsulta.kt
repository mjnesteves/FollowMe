package com.followme.data.historicomedico

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.followme.R


fun validarConsulta(
    especialidade: String,
    hospital: String,
    horaConsulta: String,
    dataConsulta: String
): Boolean {
    return especialidade != "" && hospital != "" && horaConsulta != "" && dataConsulta != ""
}

