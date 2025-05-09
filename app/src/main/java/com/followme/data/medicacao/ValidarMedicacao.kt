package com.followme.data.medicacao

import com.followme.R

fun validarMedicacao(
    nomeMedicamento: String,
    quantidade: Int,
    dataFim: Long,
    onInvalidate: (Int) -> Unit,
    onValidate: () -> Unit
) {
    if (nomeMedicamento.isEmpty()) {
        onInvalidate(R.string.nomeMedicamento)
        return
    }

    if (quantidade < 1) {
        onInvalidate(R.string.quantidade)
        return
    }

    if (dataFim < 1) {
        onInvalidate(R.string.data_fim)
        return
    }
    onValidate()
}