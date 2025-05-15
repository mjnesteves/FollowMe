package com.followme.data.dataBase

import kotlinx.coroutines.flow.Flow

/**
 * Repository that provides insert, update, delete, and retrieve of [Consulta] from a given data source.
 */
interface DataBaseRepository {
    /**
     * Retrieve all the consultas from the the given data source.
     */
    fun getAllConsultasStream(): Flow<List<Consulta>>

    /**
     * Retrieve a Consulta from the given data source that matches with the [id].
     */
    fun getConsultaStream(id: Int): Flow<Consulta?>

    fun getAllConsultasUser(id: Int): Flow<List<Consulta?>>

    /**
     * Insert consulta in the data source
     */
    suspend fun insertConsulta(consulta: Consulta)

    /**
     * Delete consulta from the data source
     */


    suspend fun deleteConsulta(consulta: Consulta)

    /**
     * Update consulta in the data source
     */
    suspend fun updateConsulta(consulta: Consulta)


    /**
     * Retrieve all the medicamentos from the the given data source.
     */
    fun getAllMedicamentosStream(): Flow<List<Medicamento>>

    /**
     * Retrieve a Medicamento from the given data source that matches with the [id].
     */
    fun getMedicamentoStream(id: Int): Flow<Medicamento?>


    fun getAllMedicamentosUser(id: Int): Flow<List<Medicamento?>>


    /**
     * Insert Medicamento in the data source
     */
    suspend fun insertMedicamento(medicamento: Medicamento)

    /**
     * Delete Medicamento from the data source
     */
    suspend fun deleteMedicamento(medicamento: Medicamento)

    /**
     * Update Medicamento in the data source
     */
    suspend fun updateMedicamento(medicamento: Medicamento)

    /**
     * Retrieve all the SinaisVitais from the the given data source.
     */
    fun getAllSinaisVitaisStream(): Flow<List<SinaisVitais>>

    /**
     * Retrieve an SinaisVitais from the given data source that matches with the [id].
     */
    fun getSinaisVitaisStream(id: Int): Flow<SinaisVitais?>

    /**
     * Insert SinaisVitais in the data source
     */
    suspend fun insertSinaisVitais(sinaisVitais: SinaisVitais)

    /**
     * Delete SinaisVitais from the data source
     */
    suspend fun deleteSinaisVitais(sinaisVitais: SinaisVitais)

    /**
     * Update SinaisVitais in the data source
     */
    suspend fun updateSinaisVitais(sinaisVitais: SinaisVitais)

    /**
     * Retrieve all the Utilizador from the the given data source.
     */
    fun getAllUtilizadoresStream(): Flow<List<Utilizador?>>

    /**
     * Retrieve an Utilizador from the given data source that matches with the [id].
     */
    fun getUtilizadorStream(id: Int): Flow<Utilizador?>

    /**
     * Insert Utilizador in the data source
     */
    suspend fun insertUtilizador(utilizador: Utilizador)

    /**
     * Delete Utilizador from the data source
     */
    suspend fun deleteUtilizador(utilizador: Utilizador)

    /**
     * Update Utilizador in the data source
     */
    suspend fun updateUtilizador(utilizador: Utilizador)

}