package com.followme.data.dataBase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface DataBaseDao {

    //Consulta
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: Consulta)

    @Update
    suspend fun update(item: Consulta)

    @Delete
    suspend fun delete(item: Consulta)


    @Query("SELECT * from Consulta WHERE idConsulta = :id")
    fun getConsulta(id: Int): Flow<Consulta>

    @Query("SELECT * from Consulta ORDER BY idConsulta ASC")
    fun getAllConsultas(): Flow<List<Consulta>>


    // Medicamento
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Medicamento)

    @Update
    suspend fun update(item: Medicamento)

    @Delete
    suspend fun delete(item: Medicamento)


    @Query("SELECT * from Medicamento WHERE idMedicamento = :idMedicamento")
    fun getMedicamento(idMedicamento: Int): Flow<Medicamento>

    @Query("SELECT * from Medicamento ORDER BY idMedicamento ASC")
    fun getAllMedicamentos(): Flow<List<Medicamento>>


    //Sinais Vitais
    @Insert(onConflict = OnConflictStrategy.IGNORE)
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
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Utilizador)

    @Update
    suspend fun update(item: Utilizador)

    @Delete
    suspend fun delete(item: Utilizador)


    @Query("SELECT * from Utilizador WHERE idUtilizador = :id")
    fun getUtilizador(id: Int): Flow<Utilizador>

    @Query("SELECT * from Utilizador ORDER BY idUtilizador ASC")
    fun getAllUtilizadores(): Flow<List<Utilizador>>


}