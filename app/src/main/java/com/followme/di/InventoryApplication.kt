package com.followme.di

import android.app.Application
import com.followme.data.AppContainer
import com.followme.data.AppDataContainer
import com.google.firebase.FirebaseApp


class InventoryApplication : Application() {

    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */

    lateinit var container: AppContainer


    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
        FirebaseApp.initializeApp(this)

    }
}
