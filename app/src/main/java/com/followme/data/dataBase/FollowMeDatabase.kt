package com.followme.data.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Database class with a singleton Instance object.
 */
@Database(
    entities = [Consulta::class, Medicamento::class, Utilizador::class, SinaisVitais::class],
    version = 6,
    exportSchema = false
)
abstract class FollowMeDatabase : RoomDatabase() {

    abstract fun dataBaseDao(): DataBaseDao

    companion object {
        @Volatile
        private var Instance: FollowMeDatabase? = null

        fun getDatabase(context: Context): FollowMeDatabase {
            return Instance ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FollowMeDatabase::class.java,
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