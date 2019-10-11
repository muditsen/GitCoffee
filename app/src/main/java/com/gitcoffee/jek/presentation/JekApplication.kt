package com.gitcoffee.jek.presentation

import android.app.Application
import com.gitcoffee.jek.network.VolleySingleton

class JekApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        VolleySingleton.init(this)
    }
}