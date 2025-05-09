package com.followme.data.historicomedico

import com.followme.R

fun validarConsulta(
    especialidade: String,
    hospital: String,
    dataConsulta: Long,
    onInvalidate: (Int) -> Unit,
    onValidate: () -> Unit
) {
    if (especialidade.isEmpty()) {
        onInvalidate(R.string.especialidade)
        return
    }

    if (hospital.isEmpty()) {
        onInvalidate(R.string.hospital)
        return
    }

    if (dataConsulta < 1) {
        onInvalidate(R.string.dataConsulta)
        return
    }
    onValidate()
}