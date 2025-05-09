package com.followme

import android.app.Application
import com.followme.data.dataBase.AppContainer
import com.followme.data.dataBase.AppDataContainer


class InventoryApplication : Application() {

    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */

    lateinit var container: AppContainer


    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)

    }
}
