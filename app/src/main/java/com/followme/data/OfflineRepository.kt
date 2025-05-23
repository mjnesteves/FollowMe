package com.followme.data

import com.followme.data.entidades.Consulta
import com.followme.data.entidades.Medicamento
import com.followme.data.entidades.SinaisVitais
import com.followme.data.entidades.Utilizador
import kotlinx.coroutines.flow.Flow

/*
Funções para operações com a base de dados de forma assíncrona

 */

class OfflineRepository(private val appDao: AppDao) : AppRepository {
    override fun getAllConsultas(): Flow<List<Consulta>> = appDao.getAllConsultas()

    override fun getAllConsultasUser(id: Int): Flow<List<Consulta>> =
        appDao.getAllConsultasUser(id)

    override fun getConsulta(id: Int): Flow<Consulta?> = appDao.getConsulta(id)

    override suspend fun insertConsulta(consulta: Consulta) = appDao.insert(consulta)

    override suspend fun deleteConsulta(consulta: Consulta) = appDao.delete(consulta)

    override suspend fun updateConsulta(consulta: Consulta) = appDao.update(consulta)

    override fun getAllMedicamentos(): Flow<List<Medicamento>> =
        appDao.getAllMedicamentos()

    override fun getMedicamento(id: Int): Flow<Medicamento?> = appDao.getMedicamento(id)


    override fun getAllMedicamentosUser(id: Int): Flow<List<Medicamento?>> =
        appDao.getAllMedicamentosUser(id)

    override suspend fun insertMedicamento(medicamento: Medicamento) =
        appDao.insert(medicamento)

    override suspend fun deleteMedicamento(medicamento: Medicamento) =
        appDao.delete(medicamento)

    override suspend fun updateMedicamento(medicamento: Medicamento) =
        appDao.update(medicamento)

    override fun getAllSinaisVitais(): Flow<List<SinaisVitais>> =
        appDao.getSinaisVitais()

    override fun getSinaisVitais(id: Int): Flow<SinaisVitais?> = appDao.getSinalVital(id)

    override suspend fun insertSinaisVitais(sinaisVitais: SinaisVitais) =
        appDao.insert(sinaisVitais)

    override suspend fun deleteSinaisVitais(sinaisVitais: SinaisVitais) =
        appDao.delete(sinaisVitais)

    override suspend fun updateSinaisVitais(sinaisVitais: SinaisVitais) =
        appDao.update(sinaisVitais)

    override fun getAllUtilizadoresStream(): Flow<List<Utilizador>> =
        appDao.getAllUtilizadores()

    override fun getUtilizador(id: Int): Flow<Utilizador?> = appDao.getUtilizador(id)

    override suspend fun insertUtilizador(utilizador: Utilizador) = appDao.insert(utilizador)

    override suspend fun deleteUtilizador(utilizador: Utilizador) = appDao.delete(utilizador)

    override suspend fun updateUtilizador(utilizador: Utilizador) = appDao.update(utilizador)

}




