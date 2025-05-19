package com.followme.di

import android.app.Application
import com.google.firebase.FirebaseApp


class AppInstanceLaucher : Application() {

    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */

    lateinit var container: AppContainer


    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)

        //Iniciar Firebase
        FirebaseApp.initializeApp(this)

    }
}
