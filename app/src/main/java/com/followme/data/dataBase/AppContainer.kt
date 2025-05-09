package com.followme.data.dataBase;

import android.content.Context


/**
 * App container for Dependency injection.
 */
interface AppContainer {
    val dataBaseRepository: DataBaseRepository
}

/**
 * [AppContainer] implementation that provides instance of [OfflineItemsRepository]
 */
class AppDataContainer(private val context: Context) : AppContainer {
    /**
     * Implementation for [ItemsRepository]
     */
    override val dataBaseRepository: DataBaseRepository by lazy {
        OfflineRepository(FollowMeDatabase.getDatabase(context).dataBaseDao())
    }

}
