package com.followme.di

import android.content.Context
import com.followme.data.AppDatabase
import com.followme.data.AppRepository
import com.followme.data.OfflineRepository


/**
 * App container for Dependency injection.
 */
interface AppContainer {
    val repositorio: AppRepository
}

/**
 * AppContainer implementation that provides instance of OfflineItemsRepository
 */
class AppDataContainer(private val context: Context) : AppContainer {
    /**
     * Implementation for ItemsRepository
     */
    override val repositorio: AppRepository by lazy {
        OfflineRepository(AppDatabase.getDatabase(context).dataBaseDao())
    }

}



