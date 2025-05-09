package com.followme.data.medicacao

enum class TempoDia {
    PequenoAlmoco,
    Almoco,
    Lanche,
    Jantar
}

fun getTempoDia(): List<TempoDia> {
    val listaTempoDia = mutableListOf<TempoDia>()
    listaTempoDia.add(TempoDia.PequenoAlmoco)
    listaTempoDia.add(TempoDia.Almoco)
    listaTempoDia.add(TempoDia.Lanche)
    listaTempoDia.add(TempoDia.Jantar)

    return listaTempoDia
}