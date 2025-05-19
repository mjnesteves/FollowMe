package com.followme.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.followme.data.entidades.Consulta
import com.followme.data.entidades.Medicamento
import com.followme.data.entidades.SinaisVitais
import com.followme.data.entidades.Utilizador
import kotlinx.coroutines.flow.Flow

/*
Data Access Objects.
Funcões para acesso à base de dados
Inclui operações elementares e Queries específicas
 */


@Dao
interface AppDao {

    //Consulta
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: Consulta)

    @Update
    suspend fun update(item: Consulta)

    @Delete
    suspend fun delete(item: Consulta)


    @Query("SELECT * from Consulta WHERE idConsulta = :id")
    fun getConsulta(id: Int): Flow<Consulta?>


    @Query("SELECT * from Consulta ORDER BY idConsulta ASC")
    fun getAllConsultas(): Flow<List<Consulta>>

    @Query("SELECT * from Consulta WHERE idUtilizador = :id")
    fun getAllConsultasUser(id: Int): Flow<List<Consulta>>

    // Medicamento
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: Medicamento)

    @Update
    suspend fun update(item: Medicamento)

    @Delete
    suspend fun delete(item: Medicamento)


    @Query("SELECT * from Medicamento WHERE idMedicamento = :idMedicamento")
    fun getMedicamento(idMedicamento: Int): Flow<Medicamento?>


    @Query("SELECT * from Medicamento ORDER BY idMedicamento ASC")
    fun getAllMedicamentos(): Flow<List<Medicamento>>


    @Query("SELECT * from Medicamento WHERE idUtilizador = :id")
    fun getAllMedicamentosUser(id: Int): Flow<List<Medicamento?>>


    //Sinais Vitais
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: SinaisVitais)

    @Update
    suspend fun update(item: SinaisVitais)

    @Delete
    suspend fun delete(item: SinaisVitais)


    @Query("SELECT * from SinaisVitais WHERE idRegisto = :id")
    fun getSinalVital(id: Int): Flow<SinaisVitais>

    @Query("SELECT * from SinaisVitais ORDER BY idRegisto ASC")
    fun getSinaisVitais(): Flow<List<SinaisVitais>>


    //Utilizador
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: Utilizador)

    @Update
    suspend fun update(item: Utilizador)

    @Delete
    suspend fun delete(item: Utilizador)


    @Query("SELECT * from Utilizador WHERE idUtilizador = :id")
    fun getUtilizador(id: Int): Flow<Utilizador?>

    @Query("SELECT * from Utilizador ORDER BY idUtilizador ASC")
    fun getAllUtilizadores(): Flow<List<Utilizador>>



}