package com.followme.data

import com.followme.data.entidades.Consulta
import com.followme.data.entidades.Medicamento
import com.followme.data.entidades.SinaisVitais
import com.followme.data.entidades.Utilizador
import kotlinx.coroutines.flow.Flow

/**
 * Repository that provides insert, update, delete, and retrieve of [Consulta] from a given data source.
 */
interface AppRepository {
    /**
     * Retrieve all the consultas from the the given data source.
     */
    fun getAllConsultas(): Flow<List<Consulta>>

    /**
     * Retrieve a Consulta from the given data source that matches with the [id].
     */
    fun getConsulta(id: Int): Flow<Consulta?>

    fun getAllConsultasUser(id: Int): Flow<List<Consulta>>

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
    fun getAllMedicamentos(): Flow<List<Medicamento>>

    /**
     * Retrieve a Medicamento from the given data source that matches with the [id].
     */
    fun getMedicamento(id: Int): Flow<Medicamento?>


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
    fun getAllSinaisVitais(): Flow<List<SinaisVitais>>

    /**
     * Retrieve an SinaisVitais from the given data source that matches with the [id].
     */
    fun getSinaisVitais(id: Int): Flow<SinaisVitais?>

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
    fun getUtilizador(id: Int): Flow<Utilizador?>

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