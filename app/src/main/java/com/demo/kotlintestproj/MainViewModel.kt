package com.demo.kotlintestproj

import Injections.WebserviceComponent
import Repository.Model.Picture
import Repository.PictureRepo
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import io.reactivex.Single
import javax.inject.Inject

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private var app : App = application as App
    private var component : WebserviceComponent

    @Inject
    lateinit var repo : PictureRepo

    init {
        component = app.getWebserviceComponent()
        component.inject(this)
        Log.i("MainViewModel", "MainViewModel created")
    }

    fun loadImages(page : Int) : Single<ArrayList<Picture>> {
        return repo.loadImages(page)
    }

    fun loadImageById(picId : Int) : Single<Picture> {
        return repo.loadImageById(picId)
    }

    fun getRowCountInDb() : Single<Int> {
        return repo.getRowCountInDb()
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("MainViewModel", "MainViewModel destroyed")
    }

}