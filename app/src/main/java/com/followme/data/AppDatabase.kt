package com.followme.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.followme.data.entidades.Consulta
import com.followme.data.entidades.Medicamento
import com.followme.data.entidades.SinaisVitais
import com.followme.data.entidades.Utilizador

/**
 * Database class with a singleton Instance object.
 */
@Database(
    entities = [Consulta::class, Medicamento::class, Utilizador::class, SinaisVitais::class],
    version = 8,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun dataBaseDao(): AppDao

    companion object {
        @Volatile
        private var Instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return Instance ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "FollowMe_Database"

                )
                    .fallbackToDestructiveMigration(true)
                    .build()
                Instance = instance
                instance
            }
        }
    }
}