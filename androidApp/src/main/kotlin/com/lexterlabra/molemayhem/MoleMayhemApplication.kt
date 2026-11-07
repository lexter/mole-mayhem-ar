// Created by Lexter Labra
package com.lexterlabra.molemayhem

import android.app.Application
import com.lexterlabra.molemayhem.ui.di.initKoin

class MoleMayhemApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }
}
