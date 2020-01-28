package com.demo.kotlintestproj

import Injections.DaggerWebserviceComponent
import Injections.WebserviceComponent
import Injections.WebserviceModule
import android.app.Application

class App : Application() {

    private lateinit var component : WebserviceComponent

    override fun onCreate() {
        super.onCreate()
        component = DaggerWebserviceComponent.builder().webserviceModule(WebserviceModule(this)).build()
    }

    fun getWebserviceComponent() : WebserviceComponent {
        return component
    }

}