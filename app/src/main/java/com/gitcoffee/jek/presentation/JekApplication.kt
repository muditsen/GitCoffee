package com.gitcoffee.jek.presentation

import android.app.Application
import com.gitcoffee.jek.presentation.di.AppModule
import com.gitcoffee.jek.network.VolleySingleton
import com.gitcoffee.jek.presentation.di.DaggerAppComponent

class JekApplication: Application() {

    private lateinit var daggerAppComponent: DaggerAppComponent

    override fun onCreate() {
        super.onCreate()
        VolleySingleton.init(this)
        daggerAppComponent = DaggerAppComponent.builder().appModule(
            AppModule(
                this
            )
        ).build() as DaggerAppComponent
    }

    fun getAppComponent():DaggerAppComponent{
        return daggerAppComponent
    }
}

