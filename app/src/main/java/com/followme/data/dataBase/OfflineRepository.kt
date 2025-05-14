package com.followme.data.dataBase

import kotlinx.coroutines.flow.Flow

class OfflineRepository(private val dataBaseDao: DataBaseDao) : DataBaseRepository {
    override fun getAllConsultasStream(): Flow<List<Consulta>> = dataBaseDao.getAllConsultas()

    override fun getAllConsultasUser(id: Int): Flow<List<Consulta?>> =
        dataBaseDao.getAllConsultasUser(id)

    override fun getConsultaStream(id: Int): Flow<Consulta?> = dataBaseDao.getConsulta(id)

    override suspend fun insertConsulta(consulta: Consulta) = dataBaseDao.insert(consulta)

    override suspend fun deleteConsulta(consulta: Consulta) = dataBaseDao.delete(consulta)

    override suspend fun updateConsulta(consulta: Consulta) = dataBaseDao.update(consulta)

    override fun getAllMedicamentosStream(): Flow<List<Medicamento>> =
        dataBaseDao.getAllMedicamentos()

    override fun getMedicamentoStream(id: Int): Flow<Medicamento?> = dataBaseDao.getMedicamento(id)

    override suspend fun insertMedicamento(medicamento: Medicamento) =
        dataBaseDao.insert(medicamento)

    override suspend fun deleteMedicamento(medicamento: Medicamento) =
        dataBaseDao.delete(medicamento)

    override suspend fun updateMedicamento(medicamento: Medicamento) =
        dataBaseDao.update(medicamento)

    override fun getAllSinaisVitaisStream(): Flow<List<SinaisVitais>> =
        dataBaseDao.getSinaisVitais()

    override fun getSinaisVitaisStream(id: Int): Flow<SinaisVitais?> = dataBaseDao.getSinalVital(id)

    override suspend fun insertSinaisVitais(sinaisVitais: SinaisVitais) =
        dataBaseDao.insert(sinaisVitais)

    override suspend fun deleteSinaisVitais(sinaisVitais: SinaisVitais) =
        dataBaseDao.delete(sinaisVitais)

    override suspend fun updateSinaisVitais(sinaisVitais: SinaisVitais) =
        dataBaseDao.update(sinaisVitais)

    override fun getAllUtilizadoresStream(): Flow<List<Utilizador?>> =
        dataBaseDao.getAllUtilizadores()

    override fun getUtilizadorStream(id: Int): Flow<Utilizador?> = dataBaseDao.getUtilizador(id)

    override suspend fun insertUtilizador(utilizador: Utilizador) = dataBaseDao.insert(utilizador)

    override suspend fun deleteUtilizador(utilizador: Utilizador) = dataBaseDao.delete(utilizador)

    override suspend fun updateUtilizador(utilizador: Utilizador) = dataBaseDao.update(utilizador)

}




