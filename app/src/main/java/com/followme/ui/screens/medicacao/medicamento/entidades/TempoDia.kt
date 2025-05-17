package com.followme.ui.screens.medicacao.medicamento.entidades

enum class TempoDia(val displayName: String) {
    PequenoAlmoco("Pequeno Almoço"),
    Almoco("Almoço"),
    Lanche("Lanche"),
    Jantar("Jantar")
}

fun getTempoDia(): List<TempoDia> {
    return TempoDia.entries
}