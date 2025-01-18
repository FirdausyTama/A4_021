package com.example.propertiapp

import android.app.Application
import com.example.propertiapp.di.AppContainer
import com.example.propertiapp.di.PropertiContainer

class PropertiApplication:Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = PropertiContainer()
    }
}